package com.bytewave.staffbrief.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.domain.model.Person
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    viewModel.updateCategoryList()
    val soldiers by viewModel.soldiers.collectAsState()
    val categories by viewModel.categoryWithIndex.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
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
                onClick = { navController.navigate(Routes.CreateSoldier.createRoute(0L)) })
        }

    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                value = query,
                onValueChange = { viewModel.onChangeSearchQuery(it) },
                placeholder = { Text("Поиск...") },
                leadingIcon = { Icon(Icons.Default.Search, "Поиск")},
                trailingIcon = { IconButton(onClick = {viewModel.clearSearchQuery()}) {
                    Icon(Icons.Default.Clear, contentDescription = "Очистить")
                }},
                modifier = Modifier.padding(4.dp).fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            LazyRow {
                items(categories) { category ->
                    FilterChip(
                        modifier = Modifier.padding(2.dp),
                        selected = category.second,
                        onClick = { viewModel.onChangeCategoryFlag(category.first, !category.second)},
                        label = { Text(category.first.name) }
                    )
                }
            }
            LazyColumn {
                items(soldiers) { soldier ->
                    SoldierCard(soldier) { id ->
                        navController.navigate(
                            Routes.Soldier.createRoute(
                                id
                            )
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun SoldierCard(soldier: Person, navigateToSoldier: (soldierId :Long) -> Unit){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth().height(50.dp).padding(6.dp),
        onClick = {navigateToSoldier(soldier.id)}
    ) {
        Text(
            text = "${soldier.id}, ${soldier.lastName} ${soldier.firstName} ${soldier.patronymic}",
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.Center,
        )

    }
}