package com.android.citypulse.feature_event.presentation.events.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.citypulse.R

@Composable
fun ShowConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(
                text = stringResource(id = R.string.delete_private_event_confirmation_question),
                textAlign = TextAlign.Center,
            )
        },
        buttons = {
            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 60.dp),
                    onClick = {
                        onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple))
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_private_event_confirm),
                        color = colorResource(id = R.color.white)
                    )
                }
                Spacer(modifier = Modifier.width(80.dp))
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 50.dp),
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple))
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_private_event_dismiss),
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    )
}