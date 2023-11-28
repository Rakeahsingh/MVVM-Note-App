package com.example.mvvmnoteapp.feature_note.presentation.addNoteScreen

import androidx.compose.ui.graphics.toArgb
import com.example.mvvmnoteapp.feature_note.domain.model.Note

data class AddNoteState(

    val title: String = "",
    val titleHint: String = "Enter Title...",
    val hintVisibility: Boolean = true,
    val description: String = "",
    val descriptionHint: String = "Enter Description...",
    val color: Int = Note.color.random().toArgb()

)
