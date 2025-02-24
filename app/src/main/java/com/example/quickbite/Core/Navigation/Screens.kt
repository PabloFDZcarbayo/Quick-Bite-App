package com.example.quickbite.Core.Navigation

import com.example.quickbite.Data.Remote.SpoonacularService
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object SingUp

@Serializable
object Home

@Serializable
object Recipes_Categories

@Serializable
data class List_Of_Recipes (val name:String)


@Serializable
data class RecipeDetail(val recipe : SpoonacularService.Recipe)