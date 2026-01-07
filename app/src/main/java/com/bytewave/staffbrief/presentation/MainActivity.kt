package com.bytewave.staffbrief.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bytewave.staffbrief.presentation.navigation.NavGraph
import com.bytewave.staffbrief.presentation.viewmodels.ThemeViewModel
import com.bytewave.staffbrief.ui.theme.StaffBriefTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity() : ComponentActivity() {
    private val viewModel: ThemeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                StaffBriefTheme(viewModel.isDarkTheme.value) {
                    Main(
                        onToggleTheme = { viewModel.toggleTheme()}
                    )
                }
            }
        }
    }
}
@Composable
fun Main(onToggleTheme: () -> Unit){
    val navController = rememberNavController()
    NavGraph(navController = navController, onToggleTheme)
}