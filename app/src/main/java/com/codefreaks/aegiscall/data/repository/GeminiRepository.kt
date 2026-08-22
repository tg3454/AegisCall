package com.codefreaks.aegiscall.data.repository

import com.codefreaks.aegiscall.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class GeminiRepository {

    private val model = GenerativeModel(
        modelName = "gemini-3.6-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun analyzeTranscript(transcript: String): String {

        val prompt = """
            You are AegisCall, an AI system that detects scam calls.

            Analyze this phone conversation for scam indicators.

            Look for:
            - authority impersonation
            - threats
            - urgency
            - secrecy or isolation
            - financial pressure
            - OTP/PIN/bank requests
            - identity information requests
            - remote access requests
            - caller intent
            - scam type
            - manipulation tactics

            Do not classify something as a scam just because
            words like police, CBI, bank, or arrest appear.
            Analyze the overall intent and behavior.

            Return ONLY valid JSON:

            {
              "riskScore": 0,
              "scamType": "UNKNOWN",
              "callerIntent": "",
              "tactics": [],
              "sensitiveRequest": {
                "detected": false,
                "type": "NONE"
              },
              "escalationLevel": "NONE",
              "evidence": [],
              "victimAdvice": "",
              "manipulation": {
                "fear": 0,
                "urgency": 0,
                "authority": 0,
                "isolation": 0,
                "pressure": 0
              }
            }

            Transcript:
            $transcript
        """.trimIndent()

        val response = model.generateContent(prompt)

        return response.text ?: "{}"
    }
}