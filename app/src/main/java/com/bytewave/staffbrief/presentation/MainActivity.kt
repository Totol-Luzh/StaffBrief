package com.bytewave.staffbrief.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bytewave.staffbrief.ui.theme.StaffBriefTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                StaffBriefTheme {
                    Main()
                }
            }
        }
    }
}
@Composable
fun Main(){

    val navController = rememberNavController()
    Column(Modifier.padding(0.dp)) {
        NavHost(navController = navController, startDestination = Routes.Home.route, modifier = Modifier.weight(1f)) {

            composable(Routes.Home.route) { Home(navController) }
            composable(Routes.CreateSoldier.route,
                arguments = listOf(navArgument("personId") {type = NavType.LongType})
                ) {backStackEntry ->
                val personId = backStackEntry.arguments?.getLong("personId") ?: 0L
                CreateSoldier(personId, navController)
            }
            composable(
                route = Routes.Soldier.route,
                arguments = listOf(navArgument("personId") { type = NavType.LongType })
            ) { backStackEntry ->
                val personId = backStackEntry.arguments?.getLong("personId") ?: 0L
                Soldier(personId, navController)
            }
            composable(Routes.CategoryManagement.route) { CategoryManagement(navController) }
        }
    }
}

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object CreateSoldier : Routes("create_soldier/{personId}"){
        fun createRoute(personId: Long) = "create_soldier/$personId"
    }
    object Soldier : Routes("soldier/{personId}"){
        fun createRoute(personId: Long) = "soldier/$personId"
    }
    object CategoryManagement : Routes("about")
}