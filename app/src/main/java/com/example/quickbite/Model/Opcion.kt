package com.example.quickbite.Model

import androidx.annotation.DrawableRes

data class Opcion (
    val id:Int,
    val nombre:String,
    val descripcion:String,
    @DrawableRes
    val poster:Int
){
}