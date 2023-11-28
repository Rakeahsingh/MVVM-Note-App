package com.example.mvvmnoteapp.feature_note.presentation.noteScreen

import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.util.NoteOrder
import com.example.mvvmnoteapp.feature_note.domain.util.OrderType

data class NoteState(
    val note: List<Note> = emptyList(),
    var noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
