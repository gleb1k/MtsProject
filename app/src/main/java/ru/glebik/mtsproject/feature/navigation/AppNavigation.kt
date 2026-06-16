package ru.glebik.mtsproject.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import ru.glebik.mtsproject.feature.cell_activation.CellActivationScreen
import ru.glebik.mtsproject.feature.locker_detail.LockerDetailScreen
import ru.glebik.mtsproject.feature.main.MainScreen
import ru.glebik.mtsproject.feature.onboarding.OnboardingScreen
import ru.glebik.mtsproject.feature.profile.ProfileScreen
import ru.glebik.mtsproject.feature.auth.register.RegisterScreen

@Composable
fun AppNavigation(
    viewModel: AppNavViewModel = hiltViewModel(),
) {
    val start = viewModel.startDestination()
    val backStack = rememberNavBackStack(start)

    NavDisplay(
        modifier = Modifier.fillMaxSize(),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<OnboardingNavKey> {
                OnboardingScreen(
                    onNavigateToRegister = { backStack.add(RegisterNavKey) },
                    onNavigateToMain = {
                        backStack.clear()
                        backStack.add(MainNavKey)
                    },
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

            entry<MainNavKey> {
                MainScreen(
                    onNavigateToProfile = { backStack.add(ProfileNavKey) },
                    onNavigateToLockerDetail = { lockerId ->
                        backStack.add(LockerDetailNavKey(lockerId))
                    },
                )
            }

            entry<LockerDetailNavKey> { key ->
                LockerDetailScreen(
                    lockerId = key.lockerId,
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToCellActivation = { cell ->
                        backStack.add(CellActivationNavKey(key.lockerId, cell))
                    },
                )
            }

            entry<CellActivationNavKey> { key ->
                CellActivationScreen(
                    lockerId = key.lockerId,
                    cellNumber = key.cellNumber,
                    onNavigateBack = { backStack.removeLastOrNull() },
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
