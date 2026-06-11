package ru.glebik.mtsproject.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.ContainerRow
import ru.glebik.mtsproject.ui.common.DangerOutlinedButton
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.common.IconInfoRow
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ProfileEffect.NavigateBack -> onNavigateBack()
                ProfileEffect.NavigateToMain -> onNavigateToMain()
                ProfileEffect.NavigateToOnboarding -> onNavigateToOnboarding()
            }
        }
    }

    ProfileContent(
        state = state,
        onBackClick = { viewModel.handleIntent(ProfileIntent.Back) },
        onRentalsClick = { viewModel.handleIntent(ProfileIntent.OpenRentals) },
        onLogoutClick = { viewModel.handleIntent(ProfileIntent.Logout) },
    )
}

@Composable
private fun ProfileContent(
    state: ProfileUiState,
    onBackClick: () -> Unit,
    onRentalsClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = "",
            onBackClick = onBackClick,
            backLabel = "Назад",
            horizontalAlignment = Alignment.CenterHorizontally,
            bottomPadding = 24.dp,
            bottomContent = {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(AppTheme.colors.frame.active),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.nickname.firstOrNull()?.uppercase() ?: "",
                        style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                        color = AppTheme.colors.frame.onPrimary,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.nickname,
                    style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.frame.onPrimary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = state.email,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.frame.onPrimary,
                    textAlign = TextAlign.Center,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            UserDetailsCard(
                nickname = state.nickname,
                email = state.email,
            )

            RentalsCard(onClick = onRentalsClick)

            DangerOutlinedButton(
                text = "Выйти из аккаунта",
                iconRes = R.drawable.logout_24,
                onClick = onLogoutClick,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun UserDetailsCard(
    nickname: String,
    email: String,
) {
    ContainerColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconInfoRow(
            iconRes = R.drawable.person_24,
            iconColor = AppTheme.colors.frame.onActive,
            containerColor = AppTheme.colors.frame.active.copy(alpha = 0.3f),
            label = "Никнейм",
            value = nickname,
        )

        HorizontalDivider(color = AppTheme.colors.frame.divider)

        IconInfoRow(
            iconRes = R.drawable.email_24,
            iconColor = AppTheme.colors.icon.onBlue,
            containerColor = AppTheme.colors.icon.blue,
            label = "Email",
            value = email,
        )
    }
}

@Composable
private fun RentalsCard(
    onClick: () -> Unit,
) {
    ContainerRow(
        contentPadding = PaddingValues(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconContainer(
                iconRes = R.drawable.common_box_24,
                iconColor = AppTheme.colors.icon.onOrange,
                containerColor = AppTheme.colors.icon.orange,
            )

            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(
                    text = "Аренды",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.text.secondary,
                )

                Text(
                    text = "Мои аренды",
                    style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.text.primary,
                )
            }
        }

        Icon(
            painter = painterResource(R.drawable.rounded_arrow_forward_24),
            contentDescription = null,
            tint = AppTheme.colors.icon.onGray,
            modifier = Modifier.size(20.dp),
        )
    }
}
