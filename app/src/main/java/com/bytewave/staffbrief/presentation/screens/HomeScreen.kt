package com.bytewave.staffbrief.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.domain.model.SoldierBrief
import com.bytewave.staffbrief.presentation.navigation.Routes
import com.bytewave.staffbrief.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
    navController: NavController,
    onToggleTheme: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    viewModel.loadCategories()
    val soldiers by viewModel.soldiers.collectAsState()
    val categories by viewModel.categoryWithIndex.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(id = R.string.home_screen)) },
                actions = {
                    IconButton(onClick = { onToggleTheme() }) {
                        Icon(
                            painterResource(R.drawable.ic_themes_mode),
                            contentDescription = stringResource(R.string.change_theme),
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.CategoryManagement.route) }) {
                        Icon(
                            painterResource(R.drawable.ic_menu),
                            contentDescription = stringResource(R.string.settings),
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(
                        painterResource(R.drawable.ic_add),
                        contentDescription = stringResource(R.string.add_soldier),
                        tint = Color.Unspecified
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
                placeholder = { Text(stringResource(id = R.string.search_query)) },
                leadingIcon = { Icon(painterResource(R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.search),
                    tint = Color.Unspecified)},
                trailingIcon = {
                    if(query.isNotBlank())
                    IconButton(onClick = {viewModel.clearSearchQuery()}) {
                        Icon(painterResource(R.drawable.ic_delete_1),
                            contentDescription = stringResource(id = R.string.clear),
                            tint = Color.Unspecified)
                }},
                modifier = Modifier.padding(4.dp).fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )
            LazyRow {
                items(categories) { category ->
                    FilterChip(
                        modifier = Modifier.padding(2.dp),
                        selected = category.second,
                        onClick = { viewModel.onChangeCategoryFlag(category.first)},
                        label = { Text(
                            if(category.first.id == 0)
                                stringResource(R.string.without_category)
                            else
                                category.first.name) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = category.first.color,
                            selectedLabelColor = Color.DarkGray,
                            labelColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
            LazyColumn(Modifier.fillMaxSize(1f)) {
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
fun SoldierCard(soldier: SoldierBrief, navigateToSoldier: (soldierId :Long) -> Unit){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
        onClick = {navigateToSoldier(soldier.soldierId)}
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            soldier.photo?.let {
                Image(
                    contentDescription = null,
                    bitmap = it.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(16.dp))

                )
            }
            Text(
                text = "${soldier.lastName} ${soldier.firstName} ${soldier.patronymic}",
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }
}