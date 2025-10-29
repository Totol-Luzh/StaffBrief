package com.bytewave.staffbrief.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryManagement(
    viewModel: CategoryManagementViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val name by viewModel.categoryName.collectAsState()
    val priority by viewModel.categoryPriority.collectAsState()

    Column(
        modifier = Modifier.statusBarsPadding().padding(4.dp)
    ) {
        Text("Category management", fontSize = 30.sp)
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
                            onClick = { viewModel.deleteCategory(categoryId = category.id) }
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