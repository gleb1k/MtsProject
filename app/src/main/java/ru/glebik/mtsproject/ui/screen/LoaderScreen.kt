package ru.glebik.mtsproject.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.common.ShimmerBox
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun LoaderScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                shape = RoundedCornerShape(6.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(10.dp),
                shape = RoundedCornerShape(6.dp),
            )

            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(10.dp),
                shape = RoundedCornerShape(6.dp),
            )
        }
    }
}
