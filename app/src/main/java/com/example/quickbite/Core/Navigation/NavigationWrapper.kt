package com.example.quickbite.Core.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quickbite.View.Home_Screen
import com.example.quickbite.View.ListOfRecipes_Screen
import com.example.quickbite.View.Login_Screen
import com.example.quickbite.View.RecipesCategories_Screen
import com.example.quickbite.ViewModel.SpoonacularViewModel
import com.example.quickbite.ViewModel.UnsplashViewModel


//Esta clase sera la encargada de toda la navegacion
@Composable
fun NavigationWrapper(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            Login_Screen(modifier) { navController.navigate(Home) }

        }
        composable<Home> {
            Home_Screen(modifier) { navController.navigate(Recipes_Categories) }
        }
        composable<Recipes_Categories> {
            val viewModel: UnsplashViewModel = hiltViewModel()
            RecipesCategories_Screen(modifier, viewModel) { name ->
                navController.navigate(
                    List_Of_Recipes(name = name)
                )
            }
        }
        composable<List_Of_Recipes>
        { backStackEntry ->
            val viewModel: SpoonacularViewModel = hiltViewModel()
            val listOfRecipes: List_Of_Recipes = backStackEntry.toRoute()
            ListOfRecipes_Screen(modifier, listOfRecipes.name, viewModel)
        }

    }
}
