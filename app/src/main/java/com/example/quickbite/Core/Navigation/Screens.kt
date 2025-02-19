package com.example.quickbite.Core.Navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Home

@Serializable
object Recipes_Categories

@Serializable
data class List_Of_Recipes (val name:String)