package com.example.quickbite.Core.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quickbite.Core.Navigation.Types.RecipeType
import com.example.quickbite.Data.Remote.SpoonacularService
import com.example.quickbite.View.Home_Screen
import com.example.quickbite.View.ListOfRecipes_Screen
import com.example.quickbite.View.Login_Screen
import com.example.quickbite.View.RecipeDetail_Screen
import com.example.quickbite.View.RecipesCategories_Screen
import com.example.quickbite.View.SingUp_Screen
import com.example.quickbite.ViewModel.SpoonacularViewModel
import com.example.quickbite.ViewModel.UnsplashViewModel
import kotlin.reflect.typeOf


//Esta clase sera la encargada de toda la navegacion
@Composable
fun NavigationWrapper(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            Login_Screen(modifier,
                navigateToHome = { navController.navigate(Home) },
                navigateToSingUp = { navController.navigate(SingUp) })
        }

        composable<SingUp> {
            SingUp_Screen(modifier) { navController.navigate(Login) }
        }

        composable<Home> { backStackEntry ->
            val viewModel: UnsplashViewModel = hiltViewModel()
            Home_Screen(
                modifier, viewModel,
                navigateToRecipesCategories = { navController.navigate(Recipes_Categories) },
                navigateToRecipesList = { name -> navController.navigate(List_Of_Recipes(name = name)) }
            )
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
            ListOfRecipes_Screen(
                modifier,
                listOfRecipes.name,
                viewModel,
                onRecipeSelected = { navController.navigate(RecipeDetail(it)) })
        }


        composable<RecipeDetail>(typeMap = mapOf(typeOf<SpoonacularService.Recipe>() to RecipeType)) { backStackEntry ->
            val viewModel: SpoonacularViewModel = hiltViewModel()
            val recipeDetail: RecipeDetail = backStackEntry.toRoute()
            RecipeDetail_Screen(modifier, recipeDetail.recipe, viewModel)
        }

    }
}
