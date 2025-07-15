package com.bytewave.staffbrief

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bytewave.staffbrief.ui.theme.StaffBriefTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StaffBriefTheme {
                Main()
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
            composable(Routes.CreateSoldier.route) { CreateSoldier() }
            composable(Routes.Soldier.route) { Soldier() }
            composable(Routes.Settings.route) { Settings() }
        }
    }
}

@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Home", fontSize = 22.sp) },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.Settings.route) }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.LightGray,
                    navigationIconContentColor = Color.LightGray,
                    actionIconContentColor = Color.LightGray
                )

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add_serviceman)
                    )
                },
                onClick = { navController.navigate(Routes.CreateSoldier.route) })
        }

    ) {innerPadding ->
            LazyColumn(
                        modifier = Modifier.padding(innerPadding)
            ) {
                items(count = 20) { itemId ->
                    SoldierCard(itemId) {id -> navController.navigate(Routes.Soldier.route)}

            }
            }

    }
}

@Composable
fun SoldierCard(id: Int, navigateToSoldier: (soldierId :Int) -> Unit){
    Card(
                colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth().height(50.dp).padding(6.dp),
        onClick = {navigateToSoldier(id)}
    ) {
                Text(
                                text = "Soldier $id",
                                modifier = Modifier
                                    .padding(8.dp),
                                textAlign = TextAlign.Center,
                )

    }
}

@Composable
fun CreateSoldier() {
    Text("Create soldier Page", fontSize = 30.sp)
}

@Composable
fun Soldier() {
    Text("Soldier Page", fontSize = 30.sp)
}

@Composable
fun Settings() {
    Text("SettingsPage", fontSize = 30.sp)
}

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object CreateSoldier : Routes("create_soldier")
    object Soldier : Routes("soldier")
    object Settings : Routes("about")
}