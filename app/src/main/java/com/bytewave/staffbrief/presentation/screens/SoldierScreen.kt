package com.bytewave.staffbrief.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.domain.model.Relative
import com.bytewave.staffbrief.presentation.components.ConfirmAlertDialog
import com.bytewave.staffbrief.presentation.navigation.Routes
import com.bytewave.staffbrief.presentation.viewmodels.SoldierScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Soldier(
    soldierId: Long,
    navController: NavController,
    viewModel: SoldierScreenViewModel = koinViewModel<SoldierScreenViewModel>()
) {
    LaunchedEffect(Unit) {
        viewModel.loadSoldier(soldierId)
    }
    val soldier = viewModel.soldier.value
    val relatives = viewModel.relatives.value
    val categories = viewModel.categories.value
    val confirm by viewModel.confirmation.collectAsState()
    if (confirm.second)
        ConfirmAlertDialog(
            dialogTitle = stringResource(R.string.deleting),
            dialogText = stringResource(R.string.confirm_delete_soldier),
            onConfirmation = {
                confirm.first?.let {
                    viewModel.deleteSoldier(it)
                    navController.navigateUp()
                }
            },
            onDismissRequest = { viewModel.onConfirm(null, false) }
        )

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(R.string.soldier_screen)) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back),
                            tint = Color.Unspecified
                        )}},
                actions = {
                    IconButton(onClick = {navController.navigate(Routes.CreateSoldier.createRoute(soldierId))}) {
                        Icon(painterResource(R.drawable.ic_edit),
                            contentDescription = stringResource(id = R.string.edit_soldier),
                            tint = Color.Unspecified)}
                    IconButton(
                        onClick = { viewModel.onConfirm(soldierId, true) }) {
                        Icon(painterResource(R.drawable.ic_delete_2),
                            contentDescription = stringResource(id = R.string.delete_soldier),
                            tint = Color.Unspecified)}
                }
            )
        })
    { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                Row {
                    soldier?.photo?.let {
                        Image(
                            contentDescription = null,
                            bitmap = it.asImageBitmap(),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(6.dp)
                                .size(130.dp)
                                .clip(RoundedCornerShape(16.dp))

                        )
                    }
                    soldier?.let {
                        Text(
                            text = "${it.lastName}\n${it.firstName}\n${it.patronymic}\n${it.militaryRank.russianName}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(6.dp).fillMaxWidth()
                        )
                    }


                }
                if(!categories.isEmpty()) {
                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp
                    )
                    Text(
                        text = stringResource(R.string.included_categories),
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                        ),
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp).fillMaxWidth()
                    )
                }
                LazyRow {
                    items(categories) { category ->
                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .fillMaxWidth().padding(6.dp)
                        ) {
                            Text(text = category.name, modifier = Modifier.padding(horizontal = 4.dp))
                        }
                    }
                }
                soldier?.let {
                    Divider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.dp
                    )
                    CustomText(stringResource(R.string.birth_date), it.birthDate)
                    CustomText(stringResource(R.string.phone_number), it.phoneNumber)
                    CustomText(stringResource(R.string.general_info), it.info)
                    CustomText(stringResource(R.string.positive), it.positive)
                    CustomText(stringResource(R.string.negative), it.negative)

                }
            }
            items(relatives){relative ->
                RelativeCard(relative)
            }


        }
    }
}

@Composable
fun CustomText(hint: String, text: String?){
    text?.let {
        Text(
            text = hint,
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
            ),
            modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp).fillMaxWidth()
        )
        Text(
            text = it,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
            ),
            modifier = Modifier.padding(4.dp).fillMaxWidth()
        )
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp
        )
    }
}

@Composable
fun RelativeCard(relative: Relative){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth().padding(6.dp)
    ) {
        CustomText(stringResource(R.string.full_name), relative.fullName)
        CustomText(stringResource(R.string.kinship), relative.kinship)
        CustomText(stringResource(R.string.general_info), relative.info)
    }
}