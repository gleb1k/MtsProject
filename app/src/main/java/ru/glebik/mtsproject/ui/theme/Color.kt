package ru.glebik.mtsproject.ui.theme

import androidx.compose.ui.graphics.Color

internal val LightColorPalette = Colors(
    icon = Colors.Icon(
        blue = Color(0xFFdbeafe),
        onBlue = Color(0xFF155dfc),
        purple = Color(0xFFf3e8ff),
        onPurple = Color(0xFF9810fa),
        orange = Color(0xFFffedd4),
        onOrange = Color(0xFFf54900),
        gray = Color(0xFFBFC6CE),
        onGray = Color(0xFF99a1af),
    ),
    text = Colors.Text(
        primary = Color(0xFF1C1C1E),
        secondary = Color(0xFF6B7280),
    ),
    frame = Colors.Frame(
        primary = Color(0xFF155dfc),
        onPrimary = Color(0xFFFFFFFF),
        active = Color(0xFF86AD87),
        onActive = Color(0xFF4CAF50),
        surface = Color(0xFFFFFFFF),
        onSurface = Color(0xFF1C1C1E),
        button = Color(0xFF030213),
        onButton = Color(0xFFFFFFFF),
        background = Color(0xFFf9fafb),
        divider = Color(0xFFEFF1F4),
    )
)

internal val DarkColorPalette = Colors(
    icon = Colors.Icon(
        blue = Color(0xFF1E3A8A),
        onBlue = Color(0xFF60A5FA),
        purple = Color(0xFF4C1D95),
        onPurple = Color(0xFFA78BFA),
        orange = Color(0xFF7C2D12),
        onOrange = Color(0xFFFB923C),
        gray = Color(0xFF2A2D31),
        onGray = Color(0xFF9CA3AF),
    ),
    text = Colors.Text(
        primary = Color(0xFFE5E7EB),
        secondary = Color(0xFF9CA3AF),
    ),
    frame = Colors.Frame(
        primary = Color(0xFF3B82F6),
        onPrimary = Color(0xFF0B0F19),
        active = Color(0xFF22C55E),
        onActive = Color(0xFF052E16),
        surface = Color(0xFF1A1B1E),
        onSurface = Color(0xFFE5E7EB),
        button = Color(0xFFE5E7EB),
        onButton = Color(0xFF030213),
        background = Color(0xFF0B0F19),
        divider = Color(0xFF31363C),
    )
)

data class Colors(
    val icon: Icon,
    val text: Text,
    val frame: Frame,
) {
    data class Icon(
        val blue: Color,
        val onBlue: Color,
        val purple: Color,
        val onPurple: Color,
        val orange: Color,
        val onOrange: Color,
        val gray: Color,
        val onGray: Color,
    )

    data class Text(
        val primary: Color,
        val secondary: Color,
    )

    data class Frame(
        val primary: Color,
        val onPrimary: Color,
        val active: Color,
        val onActive: Color,
        val surface: Color,
        val onSurface: Color,
        val button: Color,
        val onButton: Color,
        val background: Color,
        val divider: Color,
    )
}
