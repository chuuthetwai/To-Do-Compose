package com.carina.to_docompose.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.carina.to_docompose.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closedDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closedDialog()
                    }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closedDialog()
                    }
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            },
            onDismissRequest = {closedDialog() }
        )
    }
}