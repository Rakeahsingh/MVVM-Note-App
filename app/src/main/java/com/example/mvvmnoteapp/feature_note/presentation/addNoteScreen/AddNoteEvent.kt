package com.example.mvvmnoteapp.feature_note.presentation.addNoteScreen

import androidx.compose.ui.focus.FocusState

sealed class AddNoteEvent{

    data class EnterTitle(val value: String): AddNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddNoteEvent()
    data class EnterDescription(val value: String): AddNoteEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddNoteEvent()
    data class ChangeColor(val color: Int): AddNoteEvent()
    object SaveNoteClick: AddNoteEvent()

}
