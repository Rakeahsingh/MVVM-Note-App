package com.example.mvvmnoteapp.feature_note.presentation.addNoteScreen

import android.annotation.SuppressLint
import com.example.mvvmnoteapp.feature_note.domain.model.Note
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmnoteapp.core.utils.UiEvent
import com.example.mvvmnoteapp.feature_note.presentation.addNoteScreen.component.TransparentHintTextField
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddNoteScreen(
    scaffoldState: ScaffoldState,
    onNavigateUp: () -> Unit,
    noteColor: Int,
    viewModel: AddNoteViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val scope = rememberCoroutineScope()

    val noteBackgroundAnimate = remember{
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.state.color)
        )
    }


    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.NavigateUp -> onNavigateUp()

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
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddNoteEvent.SaveNoteClick)
            }, backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimate.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.color.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (
                                    viewModel.state.color == colorInt
                                ) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimate.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = state.title,
                hint = state.titleHint,
                onValueChange ={
                               viewModel.onEvent(AddNoteEvent.EnterTitle(it))
                },
                onFocusChange ={
                    viewModel.onEvent(AddNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = state.hintVisibility,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = state.description,
                hint = state.descriptionHint,
                onValueChange ={
                    viewModel.onEvent(AddNoteEvent.EnterDescription(it))
                },
                onFocusChange ={
                    viewModel.onEvent(AddNoteEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = state.hintVisibility,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )

        }
    }



}