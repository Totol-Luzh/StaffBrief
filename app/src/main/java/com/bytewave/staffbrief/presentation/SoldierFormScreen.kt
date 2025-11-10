package com.bytewave.staffbrief.presentation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.data.db.converters.BitmapConverter
import com.bytewave.staffbrief.data.db.entities.Rank
import com.bytewave.staffbrief.domain.model.Relative
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
    val relatives by viewModel.relatives.collectAsState()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val bitmapImage = BitmapConverter.uriToBitmap(context, it)
            if(bitmapImage != null)
                viewModel.onPhotoChange(bitmapImage)
        }
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(R.string.soldier_screen), fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )}},
                actions = {
                    IconButton(onClick = {
                        viewModel.addSoldier()
                        navController.navigateUp() }) {
                        Icon(Icons.Default.Done, stringResource(R.string.save_form))
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
        contentWindowInsets = WindowInsets(0, 0, 0, 0))
    { innerPadding ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .imePadding()
                .padding(2.dp)
        ) {
            item {
            soldier.photo?.let {
                Image(
                    contentDescription = null,
                    bitmap = it.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = {
                            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                        })
                )
            }
            OutlinedButton(
                onClick = {
                    if (soldier.photo == null) pickMedia.launch(
                        PickVisualMediaRequest(
                            PickVisualMedia.ImageOnly
                        )
                    )
                    else viewModel.onPhotoChange(null)
                }

            ) {
                if (soldier.photo == null) Text(stringResource(R.string.add_photo)) else Text(stringResource(R.string.delete_photo))
            }

            OutlinedTextField(
                value = soldier.lastName,
                onValueChange = { viewModel.onLastNameChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.last_name)) }
            )
            OutlinedTextField(
                value = soldier.firstName,
                onValueChange = { viewModel.onFirstNameChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.first_name)) }
            )
            OutlinedTextField(
                value = soldier.patronymic,
                onValueChange = { viewModel.onPatronymicChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.patronymic)) }
            )
            RankDropDownMenu(viewModel)
            OutlinedTextField(
                value = soldier.birthDate ?: "",
                onValueChange = { viewModel.onBirthDateChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.birth_date_extended)) }
            )
            OutlinedTextField(
                value = soldier.phoneNumber ?: "",
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = soldier.info ?: "",
                onValueChange = { viewModel.onInfoChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.general_info)) }
            )
            OutlinedTextField(
                value = soldier.positive ?: "",
                onValueChange = { viewModel.onPositiveChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.positive_extended)) }
            )
            OutlinedTextField(
                value = soldier.negative ?: "",
                onValueChange = { viewModel.onNegativeChange(it) },
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.negative_extended)) }
            )
            CategoryDropDownMenu(viewModel)
        }
        itemsIndexed(relatives){index, relative ->
            CustomRelativeCard(relative, index,viewModel)
        }
        item {
            OutlinedButton(
                onClick = { viewModel.addRelative() },
            ) {
                Text(stringResource(R.string.add_relative))
            }
        }
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
            Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.rank))
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
            Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.choose_categories))
            Text(stringResource(R.string.choose_categories))
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
                            Icon(Icons.Outlined.Check, contentDescription = stringResource(R.string.selected))
                    }
                )
            }

        }
    }
}

@Composable
fun CustomRelativeCard(relative: Relative,
                       index: Int,
                       viewModel: SoldierFormViewModel){
    OutlinedCard(Modifier.padding(4.dp)) {
        TextField(
            value = relative.fullName,
            onValueChange = {viewModel.updateRelative(index, relative.copy(fullName = it))},
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.full_name_extended))}
        )
        TextField(
            value = relative.kinship,
            onValueChange = {viewModel.updateRelative(index, relative.copy(kinship = it))},
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.kinship_extended))}
        )
        TextField(
            value = relative.info,
            onValueChange = {viewModel.updateRelative(index, relative.copy(info = it))},
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.general_info))}
        )
        OutlinedButton(
            onClick = {viewModel.deleteRelativeAt(index)},
            modifier = Modifier.padding(2.dp)
        ) {Text(stringResource(R.string.delete_relative))}
    }
}