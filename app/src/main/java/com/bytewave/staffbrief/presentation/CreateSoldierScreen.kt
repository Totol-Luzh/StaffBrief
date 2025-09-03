package com.bytewave.staffbrief.presentation

import androidx.compose.foundation.Image
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateSoldier(
    navController: NavController,
    viewModel: CreateSoldierViewModel = koinViewModel()
) {
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val patronymic by viewModel.patronymic.collectAsState()
    val birthDate by viewModel.birthDate.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val info by viewModel.info.collectAsState()
    val positive by viewModel.positive.collectAsState()
    val negative by viewModel.negative.collectAsState()
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
                value = lastName,
                onValueChange = { viewModel.onLastNameChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите фамилию") }
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { viewModel.onFirstNameChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите имя") }
            )
            OutlinedTextField(
                value = patronymic,
                onValueChange = { viewModel.onPatronymicChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите отчество") }
            )
            OutlinedTextField(
                value = birthDate,
                onValueChange = { viewModel.onBirthDateChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите дату рождения: dd.mm.yyyy") }
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text("Введите номер телефона") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = info,
                onValueChange = { viewModel.onInfoChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text("Введите общую информацию") }
            )
            OutlinedTextField(
                value = positive,
                onValueChange = { viewModel.onPositiveChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text("Введите положительное о военнослужащем") }
            )
            OutlinedTextField(
                value = negative,
                onValueChange = { viewModel.onNegativeChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text("Введите отрицательное о военнослужащем") }
            )
            OutlinedButton(onClick = {viewModel.addSoldier()},
            ) { Text("Add Soldier",  fontSize = 16.sp) }
        }
    }
}