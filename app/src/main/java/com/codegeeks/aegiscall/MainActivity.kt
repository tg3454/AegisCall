package com.codegeeks.aegiscall

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.codegeeks.aegiscall.ui.auth.AuthRoute
import com.codegeeks.aegiscall.ui.theme.AegisCallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // The auth screens are brand-locked to a dark ground, so the system bars need light icons
        // regardless of the device theme.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        setContent {
            AegisCallTheme(darkTheme = true, dynamicColor = false) {
                AuthRoute()
            }
        }
    }
}
