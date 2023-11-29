package com.example.mvvmnoteapp.feature_note.presentation.noteScreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.mvvmnoteapp.feature_note.domain.model.Note


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cornerRadiusSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
) {

    Box(
        modifier = modifier
    ) {
       Canvas(modifier = modifier.matchParentSize()){
           val clipPath = Path().apply {
               lineTo(size.width - cornerRadiusSize.toPx(), 0f)
               lineTo(size.width, cornerRadiusSize.toPx())
               lineTo(size.width, size.height)
               lineTo(0f, size.height)
               close()
           }

           clipPath(clipPath){
               drawRoundRect(
                   color = Color(note.color),
                   size = size,
                   cornerRadius = CornerRadius(cornerRadius.toPx())
               )

               drawRoundRect(
                   color = Color(
                       ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                   ),
                   topLeft = Offset(size.width - cornerRadiusSize.toPx(), -100f),
                   size = Size(cornerRadiusSize.toPx() + 100f, cornerRadiusSize.toPx() + 100f),
                   cornerRadius = CornerRadius(cornerRadius.toPx())
               )

           }
       }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {

            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = modifier.height(16.dp))

            Text(
                text = note.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )

        }

        IconButton(
            onClick = onDeleteClick,
            modifier = modifier
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete_icon"
            )
        }

    }
}