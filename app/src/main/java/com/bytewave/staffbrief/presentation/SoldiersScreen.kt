package com.bytewave.staffbrief.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bytewave.staffbrief.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun Soldier(
    personId: Long,
    navController: NavController,
    viewModel: SoldierScreenViewModel = koinViewModel<SoldierScreenViewModel>()
) {
    LaunchedEffect(personId) {
        viewModel.loadSoldier(personId)
    }
    val soldier = viewModel.soldier.value

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
                    IconButton(onClick = {navController.navigate(Routes.CreateSoldier.createRoute(personId))}) {
                        Icon(Icons.Default.Edit, contentDescription = "Редактировать анкету")}
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
        Column(modifier = Modifier.padding(innerPadding)) {
            Row() {
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
                        text = "${it.lastName}\n${it.firstName}\n${it.patronymic}\n${it.rank.russianName}\n${it.birthDate}",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(6.dp).fillMaxWidth()
                    )
                }


            }
            soldier?.let {
                Text(
                    text = "${it.info}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(6.dp).fillMaxWidth()
                )
                RelativeCard()
            }

        }
    }
}

@Composable
fun RelativeCard(){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth().height(100.dp).padding(6.dp)
    ) {
        Text(text = "Information about relative",
            fontSize = 20.sp,
            modifier = Modifier.padding(6.dp))
    }
}