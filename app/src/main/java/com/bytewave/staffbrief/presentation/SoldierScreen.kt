package com.bytewave.staffbrief.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun Soldier(
    personId: Long,
    navController: NavController,
    viewModel: SoldierScreenViewModel = koinViewModel<SoldierScreenViewModel>()
) {
    viewModel.loadSoldier(personId)
    val soldier = viewModel.soldier.value
    val relatives = viewModel.relatives.value

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Soldier", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back),
                            tint = Color.Unspecified
                        )}},
                actions = {
                    IconButton(onClick = {navController.navigate(Routes.CreateSoldier.createRoute(personId))}) {
                        Icon(painterResource(R.drawable.ic_edit),
                            contentDescription = stringResource(id = R.string.edit_soldier),
                            tint = Color.Unspecified)}
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
                            text = "${it.lastName}\n${it.firstName}\n${it.patronymic}\n${it.rank.russianName}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(6.dp).fillMaxWidth()
                        )
                    }


                }
                soldier?.let {
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
        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )
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
    }
}

@Composable
fun RelativeCard(relative: Relative){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth().padding(6.dp)
    ) {
        CustomText(stringResource(R.string.full_name), relative.fullName)
        CustomText(stringResource(R.string.kinship), relative.kinship)
        CustomText(stringResource(R.string.general_info), relative.info)
    }
}