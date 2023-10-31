package com.android.citypulse

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class Event (
    @StringRes
    val time: Int,
    val band: Int,
    val location: Int,
    @DrawableRes
    val photo: Int
) {
    First(
        time = R.string.first_event_time,
        band = R.string.first_event_band,
        location = R.string.first_event_location,
        photo = R.drawable.first_event_photo
    ),
    Second(
        time = R.string.second_event_time,
        band = R.string.second_event_band,
        location = R.string.second_event_location,
        photo = R.drawable.second_event_photo
    ),
    Third(
        time = R.string.third_event_time,
        band = R.string.third_event_band,
        location = R.string.third_event_location,
        photo = R.drawable.third_event_photo
    ),
    Forth(
        time = R.string.forth_event_time,
        band = R.string.forth_event_band,
        location = R.string.forth_event_location,
        photo = R.drawable.forth_event_photo
    ),
    Fifth(
        time = R.string.fifth_event_time,
        band = R.string.fifth_event_band,
        location = R.string.fifth_event_location,
        photo = R.drawable.fifth_event_photo
    ),
    Sixth(
        time = R.string.sixth_event_time,
        band = R.string.sixth_event_band,
        location = R.string.sixth_event_location,
        photo = R.drawable.sixth_event_photo
    )
}