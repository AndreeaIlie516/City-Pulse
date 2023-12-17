package com.android.citypulse.feature_event.presentation.add_edit_event

import androidx.compose.ui.focus.FocusState

sealed class AddEditEventEvent {
    data class EnteredTime(val value: String) : AddEditEventEvent()
    data class ChangeTimeFocus(val focusState: FocusState) : AddEditEventEvent()
    data class EnteredBand(val value: String) : AddEditEventEvent()
    data class ChangeBandFocus(val focusState: FocusState) : AddEditEventEvent()
    data class EnteredLocation(val value: String) : AddEditEventEvent()
    data class ChangeLocationFocus(val focusState: FocusState) : AddEditEventEvent()
    data class EnteredImagePath(val value: String) : AddEditEventEvent()
    data class ChangeImagePathFocus(val focusState: FocusState) : AddEditEventEvent()

    object SaveEvent : AddEditEventEvent()

}