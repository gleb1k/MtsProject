package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            style = AppTheme.typography.body,
            color = AppTheme.colors.text.primary,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AppTheme.shapes.default)
                .background(AppTheme.colors.frame.background)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = if (isPassword) 40.dp else 0.dp),
                textStyle = AppTheme.typography.body.copy(
                    color = AppTheme.colors.text.primary,
                ),
                singleLine = true,
                cursorBrush = SolidColor(AppTheme.colors.frame.primary),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = if (isPassword && !isPasswordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = AppTheme.typography.body,
                            color = AppTheme.colors.text.secondary,
                        )
                    }
                    innerTextField()
                },
            )

            if (isPassword && onTogglePasswordVisibility != null) {
                IconButton(
                    onClick = onTogglePasswordVisibility,
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    Icon(
                        painter = painterResource(
                            if (isPasswordVisible) {
                                R.drawable.visibility_off_24
                            } else {
                                R.drawable.visibility_24
                            }
                        ),
                        contentDescription = null,
                        tint = AppTheme.colors.icon.onGray,
                    )
                }
            }
        }
    }
}
