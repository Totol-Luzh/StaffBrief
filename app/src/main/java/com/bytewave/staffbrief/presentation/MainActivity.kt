package com.bytewave.staffbrief.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.ui.theme.StaffBriefTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

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
            composable(Routes.CreateSoldier.route) { CreateSoldier() }
            composable(Routes.Soldier.route) { Soldier(navController) }
            composable(Routes.CategoryManagement.route) { CategoryManagement() }
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
                    IconButton(onClick = { navController.navigate(Routes.CategoryManagement.route) }) {
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
fun Soldier(navController: NavController) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Soldier", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )}},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.LightGray,
                    navigationIconContentColor = Color.LightGray,
                    actionIconContentColor = Color.LightGray
                )
            )
        })
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row() {
                Image(
                    contentDescription = null,
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(130.dp)
                        .clip(RoundedCornerShape(16.dp))

                )

                Text(
                    text = "Иванов\nМаксим\nВалерьевич\nгв. рядовой\n28.05.2005",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(6.dp).fillMaxWidth()
                )

            }
            Text(
                text = "Общая информация о военнослужащем: образование и др.",
                fontSize = 20.sp,
                modifier = Modifier.padding(6.dp).fillMaxWidth()
            )
            RelativeCard()
        }
    }
}

@Composable
fun RelativeCard(){
    Card(
                colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .fillMaxWidth().height(100.dp).padding(6.dp)
    ) {
        Text(text = "Information about relative",
            fontSize = 20.sp,
            modifier = Modifier.padding(6.dp))
    }
}
@Composable
@Preview
fun CategoryManagement(
    viewModel: CategoryManagementViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val name by viewModel.categoryName.collectAsState()
    val priority by viewModel.categoryPriority.collectAsState()

    Column(
        modifier = Modifier.statusBarsPadding().padding(4.dp)
    ) {
        Text("SettingsPage", fontSize = 30.sp)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { viewModel.onCategoryNameChange(it) },
            label = { Text("Введите категорию") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = priority.toString(),
            onValueChange = { viewModel.onPriorityChange(it) },
            label = { Text("Введите приоритет от 1 до 127 (Byte)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = { Icon(Icons.Filled.Info, contentDescription = "Приоритет влияет на порядок отображения категорий на главном экране")}
        )
        OutlinedButton(onClick = {viewModel.addCategory()},
            ) { Text("Add Category",  fontSize = 16.sp) }
        LazyColumn {
            items(categories) { category ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth().padding(4.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = category.name)
                        IconButton(
                            onClick = { viewModel.deleteCategory(categoryId = category.categoryId) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Удалить категорию"
                            )
                        }
                    }
                }
            }
        }
    }

}

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object CreateSoldier : Routes("create_soldier")
    object Soldier : Routes("soldier")
    object CategoryManagement : Routes("about")
}