package com.bytewave.staffbrief.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import com.bytewave.staffbrief.R
import com.bytewave.staffbrief.presentation.navigation.Routes

@Composable
fun  SplashScreen (navController: NavHostController ) {
    LaunchedEffect(Unit) {
        delay(2500)
        navController. navigate(Routes.Home.route){
            popUpTo("splash_screen"){
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(1f).background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            modifier = Modifier.size(110.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_app),
                contentDescription = stringResource(R.string.app_icon),
                tint = Color.Unspecified,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.app_name),
            fontSize=28.sp, fontFamily= FontFamily.Monospace, color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.product_by),
            fontSize=20.sp, fontFamily= FontFamily.Monospace, color = MaterialTheme.colorScheme.onPrimary
        )
    }
}