package com.bytewave.staffbrief.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bytewave.staffbrief.R

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
                Text(text = stringResource(R.string.cancel) )
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