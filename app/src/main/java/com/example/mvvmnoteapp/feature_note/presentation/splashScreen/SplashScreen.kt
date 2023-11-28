package com.example.mvvmnoteapp.feature_note.presentation.splashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mvvmnoteapp.R
import com.example.mvvmnoteapp.core.navigation.Route
import com.example.mvvmnoteapp.core.utils.UiEvent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier
) {

    val scale = remember{ Animatable(0f) }

    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween( durationMillis = 800, easing = {
                OvershootInterpolator(8f)
                    .getInterpolation(it)
            })
        )
        delay(2000L)
        onNavigate(UiEvent.Navigate(Route.NoteScreen))
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = modifier
                .padding(16.dp)
                .scale(scale.value)
                .size(500.dp),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "note_image"
            )
        }

        Text(
            text = "Welcome To My Note App",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.error
        )

    }

}