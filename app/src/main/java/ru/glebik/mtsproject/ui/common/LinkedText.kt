package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun LinkedTextFooter(
    prefix: String,
    linkText: String,
    linkTag: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = AppTheme.colors.text.secondary)) {
            append(prefix)
        }
        pushStringAnnotation(tag = linkTag, annotation = linkTag)
        withStyle(
            SpanStyle(
                color = AppTheme.colors.frame.primary,
                fontWeight = FontWeight.Bold,
            )
        ) {
            append(linkText)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = AppTheme.typography.body,
        modifier = modifier.fillMaxWidth(),
        onClick = { offset ->
            annotatedText.getStringAnnotations(linkTag, offset, offset)
                .firstOrNull()
                ?.let { onLinkClick() }
        },
    )
}
