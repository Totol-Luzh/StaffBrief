package com.bytewave.staffbrief.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.presentation.components.ConfirmAlertDialog
import com.bytewave.staffbrief.presentation.viewmodels.CategoryManagementViewModel
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryManagement(
    navController: NavController,
    viewModel: CategoryManagementViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val category by viewModel.category.collectAsState()
    val confirm by viewModel.confirmation.collectAsState()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val controller = rememberColorPickerController()

    if(confirm.second)
        ConfirmAlertDialog(
            dialogTitle = stringResource(R.string.deleting),
            dialogText = stringResource(R.string.confirm_delete_category),
            onConfirmation = {
                confirm.first?.let {
                    viewModel.deleteCategory(it.toInt())
                }
            },
            onDismissRequest = { viewModel.onConfirm(null, false) }
        )

    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = category.name,
                    onValueChange = { if(it.length <= 15)viewModel.onCategoryNameChange(it) },
                    label = { Text(stringResource(R.string.category_name)) },
                    singleLine = true
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(4f),
                        value = (category.priority ?: "").toString() ,
                        onValueChange = { if(it.length <= 3)viewModel.onPriorityChange(it) },
                        label = { Text(stringResource(R.string.priority)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        trailingIcon = {
                            IconButton(onClick = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.priority_info),
                                    Toast.LENGTH_LONG
                                ).show()
                            }) {
                                Icon(
                                    painterResource(R.drawable.ic_info),
                                    contentDescription = stringResource(R.string.priority_info),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    )
                    AlphaTile(
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(2f)
                            .height(60.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(6.dp)
                            ),
                        controller = controller
                    )
                    IconButton(
                        modifier = Modifier.height(40.dp),
                        onClick = {
                            Toast.makeText(
                                context,
                                context.getString(R.string.toast_category_color),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ) {
                        Icon(painterResource(R.drawable.ic_info),
                            contentDescription = stringResource(R.string.info),
                            tint = Color.Unspecified)
                    }
                    IconButton(
                        modifier = Modifier.height(40.dp),
                        onClick = {
                            controller.selectByColor(Color.White, true)
                        }
                    ) {
                        Icon(painterResource(R.drawable.ic_delete_1),
                            contentDescription = stringResource(R.string.reset_color),
                            tint = Color.Unspecified)
                    }
                }

                HsvColorPicker(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(4.dp),
                    controller = controller,
                    onColorChanged = {}
                )
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(bottom = 4.dp)) {
                    OutlinedButton(onClick = {
                        manageBottomSheet(false, scope, scaffoldState)
                        viewModel.newCategory()
                    }) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(Modifier.width(20.dp))
                    OutlinedButton(
                        onClick = {
                            viewModel.onColorChange(controller.selectedColor.value)
                            if (!viewModel.addCategory())
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.toast_category_priority),
                                    Toast.LENGTH_LONG
                                ).show()
                            manageBottomSheet(false, scope, scaffoldState)
                        },
                    ) {
                        Text(
                            stringResource(if (category.id == 0) R.string.add_category else R.string.save_category),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.category_management), fontSize = 22.sp, color = MaterialTheme.colorScheme.primary) },
              navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back),
                            tint = Color.Unspecified
                        )}},
                actions = {
                    IconButton(onClick = {
                        viewModel.newCategory()
                        manageBottomSheet(true, scope, scaffoldState)
                    }) {
                        Icon(
                            painterResource(R.drawable.ic_add),
                            contentDescription = stringResource(R.string.add_category),
                            tint = Color.Unspecified
                        )}
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.LightGray,
                    navigationIconContentColor = Color.LightGray,
                    actionIconContentColor = Color.LightGray
                )
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
                .padding(4.dp)
        )
        {

            LazyColumn {
                items(categories) { category ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(25.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.DarkGray,
                                        shape = CircleShape
                                    )
                                    .background(color = category.color, shape = CircleShape)
                            )
                            Text(text = category.name, modifier = Modifier.weight(2f))
                            Divider(
                                color = Color.Black,
                                modifier = Modifier.height(25.dp).width(1.dp).padding(4.dp)
                            )
                            Text(text = category.priority.toString())
                            IconButton(
                                onClick = {
                                    viewModel.editCategory(category)
                                    manageBottomSheet(true, scope, scaffoldState)
                                    controller.selectByColor(category.color, true)
                                }
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_edit),
                                    contentDescription = stringResource(R.string.edit_category),
                                    tint = Color.Unspecified
                                )
                            }
                            IconButton(
                                onClick = { viewModel.onConfirm(category.id.toLong(), true)}
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_delete_2),
                                    contentDescription = stringResource(R.string.delete_category),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun manageBottomSheet(isExpand: Boolean, scope: CoroutineScope, scaffoldState: BottomSheetScaffoldState){
    if(isExpand)
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    else
        scope.launch {
            scaffoldState.bottomSheetState.hide()
        }
}