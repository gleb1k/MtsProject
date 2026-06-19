package ru.glebik.mtsproject.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

data class AppTypography(
    val header: TextStyle,    // крупные заголовки экранов
    val title: TextStyle,     // заголовки карточек
    val body: TextStyle,      // основной текст
    val caption: TextStyle,   // вторичный текст
    val button: TextStyle     // кнопки
)

internal val appTypography = AppTypography(
    header = TextStyle(
        fontSize = 22.sp,
        lineHeight = 30.sp
    ),

    title = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),

    body = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),

    caption = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    button = TextStyle(
        fontSize = 15.sp,
        lineHeight = 18.sp
    )
)
