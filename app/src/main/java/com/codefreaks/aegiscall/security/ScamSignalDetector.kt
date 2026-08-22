package com.codefreaks.aegiscall.security


class ScamSignalDetector {

    private val highRiskSignals = listOf(
        "otp",
        "one time password",
        "pin",
        "upi pin",
        "cvv",
        "password",
        "bank account",
        "account number",
        "transfer money",
        "send money",
        "pay immediately",
        "urgent payment",
        "arrest",
        "arrested",
        "arrest warrant",
        "police",
        "cbi",
        "cyber crime",
        "income tax",
        "ed department",
        "money laundering",
        "do not tell",
        "don't tell",
        "keep this secret",
        "stay on the call",
        "remote access",
        "anydesk",
        "teamviewer",
        "screen sharing"
    )

    fun shouldAnalyze(transcript: String): Boolean {

        val text = transcript.lowercase()

        val matches = highRiskSignals.count { signal ->
            text.contains(signal)
        }

        return matches >= 2
    }
}