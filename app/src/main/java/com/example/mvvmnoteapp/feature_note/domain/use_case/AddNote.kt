package com.example.mvvmnoteapp.feature_note.domain.use_case

import com.example.mvvmnoteapp.feature_note.domain.model.InvalidNoteException
import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The Note Title can't be Empty")
        }
        if (note.description.isBlank()){
            throw InvalidNoteException("The Note Description can't be Empty")
        }
        return repository.insertNote(note)
    }

}