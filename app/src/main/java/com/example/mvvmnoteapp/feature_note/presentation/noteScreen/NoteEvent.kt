package com.example.mvvmnoteapp.feature_note.presentation.noteScreen

import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.util.NoteOrder

sealed class NoteEvent{

    data class OrderNote(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    object RestoreNote: NoteEvent()
    object ToggleNote: NoteEvent()

}
