package com.example.mvvmnoteapp.feature_note.data.repository

import com.example.mvvmnoteapp.feature_note.data.local.NoteDao
import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImp(
    private val dao: NoteDao
): NoteRepository {
    override fun getNote(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.gatNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }
}