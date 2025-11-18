package com.bytewave.staffbrief.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bytewave.staffbrief.presentation.screens.CategoryManagement
import com.bytewave.staffbrief.presentation.screens.CreateSoldier
import com.bytewave.staffbrief.presentation.screens.Home
import com.bytewave.staffbrief.presentation.screens.Soldier
import com.bytewave.staffbrief.presentation.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {

        composable(Routes.SplashScreen.route) {SplashScreen(navController)}
        composable(Routes.Home.route) { Home(navController) }
        composable(Routes.CreateSoldier.route,
            arguments = listOf(navArgument("soldierId") {type = NavType.LongType})
        ) {backStackEntry ->
            val personId = backStackEntry.arguments?.getLong("soldierId") ?: 0L
            CreateSoldier(personId, navController)
        }
        composable(
            route = Routes.Soldier.route,
            arguments = listOf(navArgument("soldierId") { type = NavType.LongType })
        ) { backStackEntry ->
            val personId = backStackEntry.arguments?.getLong("soldierId") ?: 0L
            Soldier(personId, navController)
        }
        composable(Routes.CategoryManagement.route) { CategoryManagement(navController) }
    }
}

sealed class Routes(val route: String) {

    object SplashScreen : Routes("splash_screen")
    object Home : Routes("home")
    object CreateSoldier : Routes("create_soldier/{soldierId}"){
        fun createRoute(soldierId: Long) = "create_soldier/$soldierId"
    }
    object Soldier : Routes("soldier/{soldierId}"){
        fun createRoute(soldierId: Long) = "soldier/$soldierId"
    }
    object CategoryManagement : Routes("about")
}