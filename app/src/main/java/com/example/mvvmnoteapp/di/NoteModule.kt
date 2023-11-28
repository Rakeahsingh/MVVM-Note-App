package com.example.mvvmnoteapp.di

import android.app.Application
import androidx.room.Room
import com.example.mvvmnoteapp.feature_note.data.local.NoteDatabase
import com.example.mvvmnoteapp.feature_note.data.repository.NoteRepositoryImp
import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.repository.NoteRepository
import com.example.mvvmnoteapp.feature_note.domain.use_case.AddNote
import com.example.mvvmnoteapp.feature_note.domain.use_case.DeleteNote
import com.example.mvvmnoteapp.feature_note.domain.use_case.GetNote
import com.example.mvvmnoteapp.feature_note.domain.use_case.GetNoteById
import com.example.mvvmnoteapp.feature_note.domain.use_case.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImp(db.dao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCase{
        return NoteUseCase(
            getNote = GetNote(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNoteById = GetNoteById(repository)
        )
    }

}