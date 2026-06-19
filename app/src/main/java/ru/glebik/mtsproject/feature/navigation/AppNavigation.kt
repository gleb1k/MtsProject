package ru.glebik.mtsproject.feature.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import ru.glebik.mtsproject.feature.auth.login.LoginScreen
import ru.glebik.mtsproject.feature.auth.register.RegisterScreen
import ru.glebik.mtsproject.feature.cell_activation.CellActivationScreen
import ru.glebik.mtsproject.feature.locker_detail.LockerDetailScreen
import ru.glebik.mtsproject.feature.main.MainScreen
import ru.glebik.mtsproject.feature.my_rents.MyRentsScreen
import ru.glebik.mtsproject.feature.onboarding.OnboardingScreen
import ru.glebik.mtsproject.feature.profile.ProfileScreen

const val TRANSITION_ANIMATION_DURATION_MS = 350

@Composable
fun AppNavigation(
    viewModel: AppNavViewModel = hiltViewModel(),
) {
    val start = viewModel.startDestination()
    val backStack = rememberNavBackStack(start)

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_ANIMATION_DURATION_MS)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { -it / 4 },
                animationSpec = tween(TRANSITION_ANIMATION_DURATION_MS),
            )
        },
        popTransitionSpec = {
            fadeIn(tween(TRANSITION_ANIMATION_DURATION_MS)) + slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_ANIMATION_DURATION_MS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TRANSITION_ANIMATION_DURATION_MS),
            ) + fadeOut(tween(TRANSITION_ANIMATION_DURATION_MS))
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(
                animationSpec = tween(TRANSITION_ANIMATION_DURATION_MS),
                initialOffsetX = { -it / 4 },
            ) togetherWith slideOutHorizontally(
                animationSpec = tween(TRANSITION_ANIMATION_DURATION_MS),
                targetOffsetX = { it },
            )
        },
        entryProvider = entryProvider {
            entry<OnboardingNavKey> {
                OnboardingScreen(
                    onNavigateToRegister = { backStack.add(RegisterNavKey) },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainNavKey)
                    },
                    onNavigateToLogin = {
                        backStack.add(LoginNavKey)
                    }
                )
            }

            entry<RegisterNavKey> {
                RegisterScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainNavKey)
                    },
                )
            }

            entry<LoginNavKey> {
                LoginScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainNavKey)
                    },
                )
            }

            entry<MainNavKey> {
                MainScreen(
                    onNavigateToProfile = { backStack.add(ProfileNavKey) },
                    onNavigateToLockerDetail = { lockerId ->
                        backStack.add(LockerDetailNavKey(lockerId))
                    },
                    onNavigateToMyRents = { backStack.add(MyRentsNavKey) },
                )
            }

            entry<MyRentsNavKey> {
                MyRentsScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                )
            }

            entry<LockerDetailNavKey> { key ->
                LockerDetailScreen(
                    lockerId = key.lockerId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToCellActivation = { cellId ->
                        backStack.add(CellActivationNavKey(cellId))
                    },
                )
            }

            entry<CellActivationNavKey> { key ->
                CellActivationScreen(
                    cellId = key.cellId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToMyRents = {
                        backStack.clear()
                        backStack.add(MainNavKey)
                        backStack.add(MyRentsNavKey)
                    },
                )
            }

            entry<ProfileNavKey> {
                ProfileScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToMain = { backStack.removeLastOrNull() },
                    onNavigateToOnboarding = {
                        backStack.clear()
                        backStack.add(OnboardingNavKey)
                    },
                )
            }
        }
    )
}
