package com.bytewave.staffbrief.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryManagement(
    navController: NavController,
    viewModel: CategoryManagementViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val name by viewModel.categoryName.collectAsState()
    val priority by viewModel.categoryPriority.collectAsState()
    val confirm by viewModel.confirmDelete.collectAsState()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Category management", fontSize = 22.sp) },
              navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )}},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.LightGray,
                    navigationIconContentColor = Color.LightGray,
                    actionIconContentColor = Color.LightGray
                )

            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(4.dp)
        )
        {
            val context = LocalContext.current
            if(confirm.second)
                ConfirmAlertDialog(
                    dialogTitle = stringResource(R.string.deleting),
                    dialogText = stringResource(R.string.confirm_delete),
                    onConfirmation = {
                        confirm.first?.let{
                            viewModel.deleteCategory(it.id) }
                        },
                    onDismissRequest = {viewModel.onConfirmChange(null, false)}
                    )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { viewModel.onCategoryNameChange(it) },
                label = { Text(stringResource(R.string.category_name)) }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = (priority ?: "").toString() ,
                onValueChange = { viewModel.onPriorityChange(it) },
                label = { Text(stringResource(R.string.priority)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.priority_info),
                            Toast.LENGTH_LONG
                        ).show()
                    }) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = stringResource(R.string.priority_info)
                        )
                    }
                }
            )
            OutlinedButton(
                onClick = {
                    if(!viewModel.addCategory())
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_category_priority),
                            Toast.LENGTH_LONG
                        ).show()

                          },
            ) { Text(stringResource(R.string.add_category), fontSize = 16.sp) }
            LazyColumn {
                items(categories) { category ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth().padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = category.name)
                            IconButton(
                                onClick = { viewModel.onConfirmChange(category, true)}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete_category)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmAlertDialog(dialogTitle: String ,
                      dialogText: String ,
                      onDismissRequest: () -> Unit ,
                      onConfirmation: () -> Unit){
    AlertDialog(
        modifier = Modifier.fillMaxWidth( 0.92f ),
        shape = RoundedCornerShape( 20. dp),
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = stringResource(R.string.yes) )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.no) )
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        }
    )
}