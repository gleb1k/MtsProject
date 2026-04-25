package ru.glebik.mtsproject.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import ru.glebik.mtsproject.ui.theme.MtsProjectTheme

enum class Screen {
    FIRST,
    SECOND
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MtsProjectTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf(Screen.FIRST) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        when (currentScreen) {

            Screen.FIRST -> {
                FirstScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNextClick = { currentScreen = Screen.SECOND }
                )
            }

            Screen.SECOND -> {
                SecondScreen(
                    modifier = Modifier.padding(innerPadding),
                    onBackClick = { currentScreen = Screen.FIRST }
                )
            }
        }
    }
}

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Первый экран")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNextClick) {
            Text("Перейти на второй")
        }
    }
}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Второй экран")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackClick) {
            Text("Назад")
        }
    }
}
