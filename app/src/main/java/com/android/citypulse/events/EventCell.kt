package com.android.citypulse.events


import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.citypulse.R

@Preview
@Composable
private fun EventCellPreview() {
    EventCellMain(
        Modifier,
        PredefinedEvent.First,
        onClickEvent = {},
        onClickFavoriteEvent = {},
        isFavorite = false
    )
}

@Composable
fun EventCellMain(
    modifier: Modifier = Modifier,
    event: PredefinedEvent,
    onClickEvent: () -> Unit,
    onClickFavoriteEvent: () -> Unit,
    isFavorite: Boolean
) {

    val drawableId =
        if (isFavorite) R.drawable.favorite_selected else R.drawable.favorite_unselected
    Row(
        modifier = Modifier
            .padding(top = 1.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.white),
                shape = AbsoluteRoundedCornerShape(
                    topLeft = 10.dp,
                    topRight = 10.dp,
                    bottomLeft = 10.dp,
                    bottomRight = 10.dp
                )
            )
            .clickable {
                onClickEvent()
            },
        verticalAlignment = Alignment.Top,

        ) {
        Image(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                .size(width = 87.dp, height = 74.dp)
                .background(
                    color = colorResource(id = R.color.light_grey),
                    shape = AbsoluteRoundedCornerShape(
                        topLeft = 10.dp,
                        topRight = 10.dp,
                        bottomLeft = 10.dp,
                        bottomRight = 10.dp
                    )
                ),
            painter = painterResource(event.photo),
            contentDescription = "Event image"
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)

        )
        {
            Text(
                text = stringResource(id = event.time),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.purple),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

            Text(
                text = stringResource(id = event.band),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

            Text(
                text = stringResource(id = event.location),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.grey),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, top = 15.dp)
                .size(30.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onClickFavoriteEvent() }
                ),

            alignment = Alignment.CenterEnd,
            painter = painterResource(id = drawableId),
            contentDescription = stringResource(id = R.string.filter_icon_description),
        )
    }
}


@Composable
fun EventCellFavorite(
    modifier: Modifier = Modifier,
    event: PredefinedEvent,
    onClickEvent: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 1.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.white),
                shape = AbsoluteRoundedCornerShape(
                    topLeft = 10.dp,
                    topRight = 10.dp,
                    bottomLeft = 10.dp,
                    bottomRight = 10.dp
                )
            )
            .clickable {
                onClickEvent()
            },
        verticalAlignment = Alignment.Top,

        ) {
        Image(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                .size(width = 87.dp, height = 74.dp)
                .background(
                    color = colorResource(id = R.color.light_grey),
                    shape = AbsoluteRoundedCornerShape(
                        topLeft = 10.dp,
                        topRight = 10.dp,
                        bottomLeft = 10.dp,
                        bottomRight = 10.dp
                    )
                ),
            painter = painterResource(event.photo),
            contentDescription = "Event image"
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)

        )
        {
            Text(
                text = stringResource(id = event.time),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.purple),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

            Text(
                text = stringResource(id = event.band),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

            Text(
                text = stringResource(id = event.location),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.grey),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )
        }
    }
}

@Composable
fun EventCellPrivate(
    modifier: Modifier = Modifier,
    event: PrivateEvent,
    onClickEvent: () -> Unit,
    onClickEditEvent: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(top = 1.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.white),
                shape = AbsoluteRoundedCornerShape(
                    topLeft = 10.dp,
                    topRight = 10.dp,
                    bottomLeft = 10.dp,
                    bottomRight = 10.dp
                )
            )
            .clickable {
                onClickEvent()
            },
        verticalAlignment = Alignment.Top,

        ) {
        if (event.photo != "") {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .size(width = 87.dp, height = 74.dp)
                    .background(
                        color = colorResource(id = R.color.light_grey),
                        shape = AbsoluteRoundedCornerShape(
                            topLeft = 10.dp,
                            topRight = 10.dp,
                            bottomLeft = 10.dp,
                            bottomRight = 10.dp
                        )
                    ),
                model = event.photo,
                placeholder = painterResource(id = R.drawable.first_event_photo),
                contentDescription = "Event image"
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)

        )
        {
            Text(
                text = event.time,
                textAlign = TextAlign.Left,
                color = colorResource(R.color.purple),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

            Text(
                text = event.band,
                textAlign = TextAlign.Left,
                color = colorResource(R.color.black),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )

            Text(
                text = event.location,
                textAlign = TextAlign.Left,
                color = colorResource(R.color.grey),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, top = 15.dp)
                .size(30.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onClickEditEvent() }
                ),

            alignment = Alignment.CenterEnd,
            painter = painterResource(id = R.drawable.edit_icon),
            contentDescription = stringResource(id = R.string.edit_icon_description),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteItem(
    modifier: Modifier = Modifier,
    event: PrivateEvent,
    onClickEvent: () -> Unit,
    onClickEditEvent: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(true) }
    val dismissState = rememberDismissState(
        confirmValueChange = {
            it == DismissValue.DismissedToStart
        }, positionalThreshold = { 150.dp.toPx() }
    )
    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                EventCellPrivate(
                    event = event,
                    onClickEvent = onClickEvent,
                    onClickEditEvent = onClickEditEvent
                )
            }
        )
    }

    LaunchedEffect(key1 = dismissState.currentValue) {
        if (dismissState.currentValue == DismissValue.DismissedToStart) {
            showDialog = true
            dismissState.reset()
        }
    }

    if (showDialog) {
        ShowConfirmationDialog(onConfirm = {
            onDelete()
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
            showDialog = false
            show = false
        }, onDismiss = {
            showDialog = false
        })
    }
}

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






