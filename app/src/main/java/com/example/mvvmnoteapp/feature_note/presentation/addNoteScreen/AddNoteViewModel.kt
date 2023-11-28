package com.example.mvvmnoteapp.feature_note.presentation.addNoteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnoteapp.core.utils.UiEvent
import com.example.mvvmnoteapp.feature_note.domain.model.InvalidNoteException
import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(AddNoteState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentNoteId : Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCase.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        state = state.copy(
                            title = note.title,
                            hintVisibility = false
                        )
                        state = state.copy(
                            description = note.description,
                            hintVisibility = false
                        )
                        state = state.copy(
                            color = note.color
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddNoteEvent){
        when(event){
            is AddNoteEvent.EnterTitle -> {
                state = state.copy(
                    title = event.value
                )
            }
            is AddNoteEvent.ChangeTitleFocus -> {
                state = state.copy(
                    hintVisibility = !event.focusState.isFocused &&
                   state.title.isBlank()
                )
            }
            is AddNoteEvent.EnterDescription -> {
                state = state.copy(
                    description = event.value
                )
            }
            is AddNoteEvent.ChangeDescriptionFocus -> {
                state = state.copy(
                    hintVisibility = !event.focusState.isFocused &&
                    state.description.isBlank()
                )
            }
            is AddNoteEvent.ChangeColor -> {
                state = state.copy(
                    color = event.color
                )
            }
            is AddNoteEvent.SaveNoteClick -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.addNote(
                            Note(
                                title = state.title,
                                description = state.description,
                                timeStamp = System.currentTimeMillis(),
                                color = state.color,
                                id = currentNoteId
                            )
                        )

                        _uiEvent.send(UiEvent.NavigateUp)

                    } catch (e: InvalidNoteException) {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Note couldn't be Save"
                            )
                        )
                    }
                }
            }
        }
    }

}