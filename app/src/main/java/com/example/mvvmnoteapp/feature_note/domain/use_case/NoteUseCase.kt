package com.example.mvvmnoteapp.feature_note.domain.use_case

data class NoteUseCase(
    val getNote: GetNote,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNoteById: GetNoteById
)
