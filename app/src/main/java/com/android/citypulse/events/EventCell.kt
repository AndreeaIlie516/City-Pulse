package com.android.citypulse.events

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.citypulse.R

@Preview
@Composable
private fun EventCellPreview() {
    EventCellMain(
        Modifier,
        Event.First,
        onClickEvent = {},
        onClickFavoriteEvent = {},
        isFavorite = false
    )
}

@Composable
fun EventCellMain(
    modifier: Modifier = Modifier,
    event: Event,
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
                    color = colorResource(id = R.color.grey),
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
    event: Event,
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
        Image(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                .size(width = 87.dp, height = 74.dp)
                .background(
                    color = colorResource(id = R.color.grey),
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
                    onClick = { onClickEditEvent() }
                ),

            alignment = Alignment.CenterEnd,
            painter = painterResource(id = R.drawable.edit_icon),
            contentDescription = stringResource(id = R.string.edit_icon_description),
        )
    }
}


