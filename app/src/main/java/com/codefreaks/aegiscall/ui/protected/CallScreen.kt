package com.codefreaks.aegiscall.ui.protected

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.codefreaks.aegiscall.call.SpeechRecognizerManager
import com.codefreaks.aegiscall.data.repository.GeminiRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import com.codefreaks.aegiscall.security.ScamSignalDetector

@Composable
fun CallScreen(
    onEndCall: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val signalDetector = remember {
        ScamSignalDetector()
    }

    val geminiRepository = remember {
        GeminiRepository()
    }

    var isCallActive by remember {
        mutableStateOf(false)
    }

    var isListening by remember {
        mutableStateOf(false)
    }

    var transcript by remember {
        mutableStateOf("")
    }

    var riskScore by remember {
        mutableIntStateOf(0)
    }

    var scamType by remember {
        mutableStateOf("UNKNOWN")
    }

    var tactics by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    var advice by remember {
        mutableStateOf("")
    }

    var isAnalyzing by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

    /*
     * Continuous speech recognizer.
     */
    val speechManager = remember {

        SpeechRecognizerManager(
            context = context,
            onText = { text ->

                transcript =
                    if (transcript.isBlank()) {
                        text
                    } else {
                        "$transcript\n$text"
                    }

                /*
                 * Send updated transcript to Gemini.
                 */
                if (signalDetector.shouldAnalyze(transcript)) {

                    isAnalyzing = true
                    error = ""

                    scope.launch {

                        try {

                            // Only send the most recent part of the
                            // conversation to Gemini to reduce token usage.
                            val analysisText =
                                if (transcript.length > 800) {
                                    transcript.takeLast(800)
                                } else {
                                    transcript
                                }

                            val response =
                                geminiRepository.analyzeTranscript(
                                    analysisText
                                )

                            parseGeminiResponse(
                                response = response,
                                onRisk = {
                                    riskScore = it
                                },
                                onScamType = {
                                    scamType = it
                                },
                                onTactics = {
                                    tactics = it
                                },
                                onAdvice = {
                                    advice = it
                                }
                            )

                        } catch (e: Exception) {

                            error =
                                e.message ?: "AI analysis failed"

                        } finally {

                            isAnalyzing = false
                        }
                    }
                }
            },
            onError = { errorMsg ->

                /*
                 * Only show critical errors.
                 * Transient errors are automatically
                 * retried by the manager.
                 */
                if (
                    errorMsg.contains(
                        "not available",
                        ignoreCase = true
                    )
                ) {
                    error = errorMsg
                    isListening = false
                    isCallActive = false
                }
            }
        )
    }

    /*
     * Clean up speech recognizer when leaving screen.
     * This prevents leaked IntentReceiver errors.
     */
    DisposableEffect(Unit) {

        onDispose {
            speechManager.stop()
        }
    }

    /*
     * Microphone permission.
     */
    val microphonePermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {

                isCallActive = true
                isListening = true
                speechManager.start()

            } else {

                error =
                    "Microphone permission is required"
            }
        }

    /*
     * Risk label.
     */
    val riskLabel = when {

        riskScore >= 80 ->
            "🔴 CRITICAL"

        riskScore >= 60 ->
            "🟠 HIGH RISK"

        riskScore >= 30 ->
            "🟡 SUSPICIOUS"

        else ->
            "🟢 LOW RISK"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "🛡️ AegisCall",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = if (isCallActive)
                "🔴 CALL ACTIVE"
            else
                "CALL READY",
            style = MaterialTheme.typography.titleLarge
        )

        /*
         * Risk card.
         */
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "SCAM RISK",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "$riskScore",
                    style = MaterialTheme.typography.displayMedium
                )

                Text(
                    text = riskLabel,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = scamType
                )
            }
        }

        /*
         * Start call.
         */
        if (!isCallActive) {

            Button(
                onClick = {

                    if (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) ==
                        PackageManager.PERMISSION_GRANTED
                    ) {

                        isCallActive = true
                        isListening = true
                        speechManager.start()

                    } else {

                        microphonePermissionLauncher.launch(
                            Manifest.permission.RECORD_AUDIO
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("🎙️ START MONITORING")
            }
        }

        if (isListening) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp)
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text("Listening continuously...")
            }
        }

        /*
         * Live transcript.
         */
        Text(
            text = "LIVE TRANSCRIPT",
            style = MaterialTheme.typography.titleLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = transcript.ifBlank {
                    "Waiting for speech..."
                },
                modifier = Modifier.padding(16.dp)
            )
        }

        /*
         * Detected tactics.
         */
        if (tactics.isNotEmpty()) {

            Text(
                text = "DETECTED TACTICS",
                style = MaterialTheme.typography.titleLarge
            )

            tactics.forEach { tactic ->

                Text(
                    text = "⚠ $tactic"
                )
            }
        }

        /*
         * AI safety coach.
         */
        if (advice.isNotBlank()) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "🛡️ AI SAFETY COACH",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(advice)
                }
            }
        }

        if (isAnalyzing) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp)
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text("Gemini analyzing...")
            }
        }

        if (error.isNotBlank()) {

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        /*
         * End monitoring.
         */
        if (isCallActive) {

            Button(
                onClick = {

                    speechManager.stop()
                    isCallActive = false
                    isListening = false

                    onEndCall()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("END MONITORING")
            }
        }
    }
}

private fun parseGeminiResponse(
    response: String,
    onRisk: (Int) -> Unit,
    onScamType: (String) -> Unit,
    onTactics: (List<String>) -> Unit,
    onAdvice: (String) -> Unit
) {

    try {

        val cleaned =
            response
                .replace("```json", "")
                .replace("```", "")
                .trim()

        val json =
            JSONObject(cleaned)

        onRisk(
            json.optInt(
                "riskScore",
                0
            )
        )

        onScamType(
            json.optString(
                "scamType",
                "UNKNOWN"
            )
        )

        val tactics =
            json.optJSONArray(
                "tactics"
            )

        val tacticList =
            mutableListOf<String>()

        if (tactics != null) {

            for (
            i in 0 until tactics.length()
            ) {

                tacticList.add(
                    tactics.getString(i)
                )
            }
        }

        onTactics(tacticList)

        onAdvice(
            json.optString(
                "victimAdvice",
                ""
            )
        )

    } catch (_: Exception) {

        // Gemini returned something that wasn't valid JSON.
    }
}