package com.bytewave.staffbrief.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            composable(Routes.CreateSoldier.route) { CreateSoldier(navController) }
            composable(Routes.Soldier.route) { Soldier(navController) }
            composable(Routes.CategoryManagement.route) { CategoryManagement() }
        }
    }
}

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object CreateSoldier : Routes("create_soldier")
    object Soldier : Routes("soldier")
    object CategoryManagement : Routes("about")
}