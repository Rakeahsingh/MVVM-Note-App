package com.example.mvvmnoteapp.feature_note.presentation.noteScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvvmnoteapp.feature_note.domain.util.NoteOrder
import com.example.mvvmnoteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title ,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.oderType)) }
            )

            Spacer(modifier = Modifier.width(4.dp))

            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.oderType)) }
            )

            Spacer(modifier = Modifier.width(4.dp))

            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.oderType)) }
            )

        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.oderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )

            Spacer(modifier = Modifier.width(4.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.oderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }

    }

}