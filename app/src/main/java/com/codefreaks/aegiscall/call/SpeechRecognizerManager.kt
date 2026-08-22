package com.codefreaks.aegiscall.call

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class SpeechRecognizerManager(
    private val context: Context,
    private val onText: (String) -> Unit,
    private val onError: (String) -> Unit
) {

    private var recognizer: SpeechRecognizer? = null
    private var isRunning = false

    fun start() {

        if (isRunning) return

        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onError("Speech recognition is not available on this device")
            return
        }

        isRunning = true

        recognizer = SpeechRecognizer.createSpeechRecognizer(context)

        recognizer?.setRecognitionListener(
            object : RecognitionListener {

                override fun onResults(results: Bundle?) {

                    val text = results
                        ?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )
                        ?.firstOrNull()

                    if (!text.isNullOrBlank()) {
                        onText(text)
                    }

                    if (isRunning) {
                        restartListening()
                    }
                }

                override fun onError(error: Int) {

                    // SpeechRecognizer frequently reports
                    // short pauses as errors. Restart automatically.
                    if (isRunning) {
                        restartListening()
                    }
                }

                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onPartialResults(
                    partialResults: Bundle?
                ) {
                    val text = partialResults
                        ?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )
                        ?.firstOrNull()

                    if (!text.isNullOrBlank()) {
                        onText(text)
                    }
                }

                override fun onEvent(
                    eventType: Int,
                    params: Bundle?
                ) {}
            }
        )

        startListening()
    }

    private fun startListening() {

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "en-IN"
            )

            putExtra(
                RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                true
            )

            putExtra(
                RecognizerIntent.EXTRA_MAX_RESULTS,
                1
            )
        }

        recognizer?.startListening(intent)
    }

    private fun restartListening() {

        recognizer?.cancel()

        startListening()
    }

    fun stop() {

        isRunning = false

        recognizer?.stopListening()
        recognizer?.cancel()
        recognizer?.destroy()

        recognizer = null
    }
}