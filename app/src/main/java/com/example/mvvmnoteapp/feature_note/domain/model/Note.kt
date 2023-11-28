package com.example.mvvmnoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvmnoteapp.ui.theme.BrightGreen
import com.example.mvvmnoteapp.ui.theme.LightYellow
import com.example.mvvmnoteapp.ui.theme.LighterGreen
import com.example.mvvmnoteapp.ui.theme.Mint
import com.example.mvvmnoteapp.ui.theme.PinkRed
import com.example.mvvmnoteapp.ui.theme.SkyBlue
import com.example.mvvmnoteapp.ui.theme.Violet


@Entity
data class Note(
    val title: String,
    val description: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null

){

    companion object{
        val color = listOf(PinkRed, SkyBlue, BrightGreen, LightYellow, LighterGreen, Mint, Violet)
    }

}

class InvalidNoteException(message: String): Exception(message)

