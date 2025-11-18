package com.bytewave.staffbrief.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bytewave.staffbrief.presentation.navigation.NavGraph
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
    NavGraph(navController = navController)
}