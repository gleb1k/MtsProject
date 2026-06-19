package ru.glebik.mtsproject.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppTopAlert(
    message: String,
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
//    AnimatedVisibility(
//        visible = visible,
//        modifier = modifier,
//        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
//        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
//    ) {
//        Text(
//            text = message,
//            style = AppTheme.typography.body.copy(fontWeight = FontWeight.Bold),
//            color = AppTheme.colors.frame.onPrimary,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(AppTheme.colors.frame.primary)
//                .statusBarsPadding()
//                .padding(horizontal = 16.dp, vertical = 14.dp),
//        )
//    }
}
