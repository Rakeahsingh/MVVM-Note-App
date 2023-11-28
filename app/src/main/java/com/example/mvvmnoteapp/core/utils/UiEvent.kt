package com.example.mvvmnoteapp.core.utils

sealed class UiEvent{

    data class Navigate(val route: String): UiEvent()

    data class ShowSnackBar(val message: String): UiEvent()

    object NavigateUp: UiEvent()

}
