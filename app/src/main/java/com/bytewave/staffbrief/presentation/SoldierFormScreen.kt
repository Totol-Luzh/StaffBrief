package com.bytewave.staffbrief.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.data.db.entities.Rank
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateSoldier(
    personId: Long,
    navController: NavController,
    viewModel: SoldierFormViewModel = koinViewModel()
) {
    if(personId != 0L){
        viewModel.loadSoldier(personId)
    }
    val soldier by viewModel.soldierState.collectAsState()
    val scrollState = rememberScrollState()

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
                actions = {
                    IconButton(onClick = {
                        viewModel.addSoldier()
                        navController.navigateUp() }) {
                        Icon(Icons.Default.Done, "Сохранить анкету")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.LightGray,
                    navigationIconContentColor = Color.LightGray,
                    actionIconContentColor = Color.LightGray
                )
            )
        })
    { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(2.dp)
            .verticalScroll(scrollState)) {
            Image(
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(6.dp)
                    .size(130.dp)
                    .clip(RoundedCornerShape(16.dp))

            )

            OutlinedTextField(
                value = soldier.lastName,
                onValueChange = { viewModel.onLastNameChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите фамилию") }
            )
            OutlinedTextField(
                value = soldier.firstName,
                onValueChange = { viewModel.onFirstNameChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите имя") }
            )
            OutlinedTextField(
                value = soldier.patronymic,
                onValueChange = { viewModel.onPatronymicChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите отчество") }
            )
            RankDropDownMenu(viewModel)
            OutlinedTextField(
                value = soldier.birthDate ?: "",
                onValueChange = { viewModel.onBirthDateChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите дату рождения: dd.mm.yyyy") }
            )
            OutlinedTextField(
                value = soldier.phoneNumber ?: "",
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите номер телефона") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = soldier.info ?: "",
                onValueChange = { viewModel.onInfoChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text("Введите общую информацию") }
            )
            OutlinedTextField(
                value = soldier.positive ?: "",
                onValueChange = { viewModel.onPositiveChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text("Введите положительное о военнослужащем") }
            )
            OutlinedTextField(
                value = soldier.negative ?: "",
                onValueChange = { viewModel.onNegativeChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text("Введите отрицательное о военнослужащем") }
            )
            CategoryDropDownMenu(viewModel)
        }
    }
}
@Composable
fun RankDropDownMenu(viewModel: SoldierFormViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val soldier by viewModel.soldierState.collectAsState()
    Box {
        OutlinedButton(onClick = { expanded = !expanded },
        ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Воинское звание")
            Text(soldier.rank.russianName)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Rank.entries.forEach { rank ->
                if(soldier.rank != rank)
                    DropdownMenuItem(
                        text = { Text(rank.russianName) },
                        onClick = {
                            viewModel.onRankChange(rank)
                            expanded = false
                        }
                    )
            }

        }
    }
}
@Composable
fun CategoryDropDownMenu(viewModel: SoldierFormViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val categories by viewModel.categoryWithIndex.collectAsState()
    Box {
        OutlinedButton(onClick = { expanded = !expanded },
            ) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Выбрать категории")
            Text("Выбрать категории")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.first.name) },
                    onClick = { viewModel.onChangeCategoryFlag(category.first, !category.second) },
                    trailingIcon = {
                        if (category.second)
                            Icon(Icons.Outlined.Check, contentDescription = null)
                    }
                )
            }

        }
    }
}