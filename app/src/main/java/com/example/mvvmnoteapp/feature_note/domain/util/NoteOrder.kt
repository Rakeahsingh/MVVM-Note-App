package com.example.mvvmnoteapp.feature_note.domain.util

sealed class NoteOrder(val oderType: OrderType){

    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder{
       return when(this){
            is Title -> Title(orderType)
            is Date -> Date(oderType)
            is Color -> Color(orderType)
        }
    }

}
