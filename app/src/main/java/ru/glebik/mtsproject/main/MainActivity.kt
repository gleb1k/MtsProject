package ru.glebik.mtsproject.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import ru.glebik.mtsproject.feature.main.MainScreen
import ru.glebik.mtsproject.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    color = AppTheme.colors.frame.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
