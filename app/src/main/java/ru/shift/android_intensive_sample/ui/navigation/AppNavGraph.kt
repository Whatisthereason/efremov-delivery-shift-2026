package ru.shift.android_intensive_sample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.shift.android_intensive_sample.ui.home.HomeScreen
import ru.shift.android_intensive_sample.ui.order.OrderStep1Screen

object Routes {
    const val HOME = "home"
    const val ORDER_STEP1 = "order_step1"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToOrderStep1 = {
                    navController.navigate(Routes.ORDER_STEP1)
                }
            )
        }

        composable(Routes.ORDER_STEP1) {
            OrderStep1Screen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
