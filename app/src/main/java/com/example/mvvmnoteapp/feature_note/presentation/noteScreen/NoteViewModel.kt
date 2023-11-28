package com.example.mvvmnoteapp.feature_note.presentation.noteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnoteapp.core.utils.UiEvent
import com.example.mvvmnoteapp.feature_note.domain.model.Note
import com.example.mvvmnoteapp.feature_note.domain.use_case.NoteUseCase
import com.example.mvvmnoteapp.feature_note.domain.util.NoteOrder
import com.example.mvvmnoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
): ViewModel() {

    var state by mutableStateOf(NoteState())
        private set

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.OrderNote -> {
                if (state.noteOrder::class == event.noteOrder::class &&
                    state.noteOrder.oderType == event.noteOrder.oderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCase.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCase.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvent.ToggleNote -> {
                state = state.copy(
                    isOrderSectionVisible = !state.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(
        noteOrder: NoteOrder
    ) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCase.getNote(noteOrder)
            .onEach {notes ->
                state = state.copy(
                    note = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}