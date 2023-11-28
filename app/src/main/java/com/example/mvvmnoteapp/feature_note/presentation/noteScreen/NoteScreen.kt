package com.example.mvvmnoteapp.feature_note.presentation.noteScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmnoteapp.core.navigation.Route
import com.example.mvvmnoteapp.core.utils.UiEvent
import com.example.mvvmnoteapp.feature_note.presentation.noteScreen.components.NoteItem
import com.example.mvvmnoteapp.feature_note.presentation.noteScreen.components.OrderSection
import com.example.mvvmnoteapp.ui.theme.BrightGreen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          onNavigate(UiEvent.Navigate(Route.AddNoteScreen))
                },
                containerColor = BrightGreen
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription ="Add_note"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Your Notes",
                    style = MaterialTheme.typography.titleLarge
                )
                
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteEvent.ToggleNote)
                    }
                ) {
                    Icon(imageVector = Icons.Default.List,
                        contentDescription = "sort_list"
                    )
                }

            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.OrderNote(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.note){note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                      onNavigate(UiEvent.Navigate(
                                           Route.AddNoteScreen
                                                   + "/${note.id}"
                                                   + "/${note.color}"
                                       ))
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NoteEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note Deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NoteEvent.RestoreNote)
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                }
            }

        }

    }

}