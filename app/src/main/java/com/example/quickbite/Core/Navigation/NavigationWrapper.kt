package com.example.quickbite.Core.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.quickbite.ViewModel.UserViewModel
import kotlin.reflect.typeOf


/* Esta clase sera la encargada de toda la navegacion, se encargara de pasar de una pantalla a otra,
* para ello utilizaremos un navController y un NavHost, al que le pasaremos las rutas */

@Composable
fun NavigationWrapper(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {

        /* El funcionamiento del NavHost consiste en crear un NavGraph, el cual contiene todas las rutas,
        * y a partir de ellas debemos crear composables que coinciden con nuestras screens,
        *  estos composable se definiran aqui en el archivo "/Core/Navigation/Screens" los nombres DEBEN coincidir */


        //Cuando una Screen requiera recibir argumentos se deben indicar tambien aqui, en la NavHost
        composable<Login> {
            val viewModel: UserViewModel = hiltViewModel()
            Login_Screen(modifier,
                viewModel,
                navigateToHome = { navController.navigate(Home) },
                navigateToSingUp = { navController.navigate(SingUp) })
        }

        composable<SingUp> {
            val viewModel: UserViewModel = hiltViewModel()
            SingUp_Screen(modifier, viewModel) { navController.navigate(Login) }
        }

        composable<Home> { backStackEntry ->
            val unsplashViewModel: UnsplashViewModel = hiltViewModel()
            val userViewModel: UserViewModel = hiltViewModel()

            // Observar el estado del usuario
            val userState by userViewModel.userState.collectAsState()

            // Si el usuario cierra sesión, redirigir a la pantalla de inicio de sesión
            LaunchedEffect(userState) {
                if (userState == null) {
                    navController.navigate(Login) {
                        popUpTo(Home) { inclusive = true } // Limpiar la pila de navegación
                    }
                }
            }

            Home_Screen(
                modifier, unsplashViewModel, userViewModel,
                navigateToRecipesCategories = { navController.navigate(Recipes_Categories) },
                navigateToRecipesList = { name -> navController.navigate(List_Of_Recipes(name = name)) },
                onLogOut = { userViewModel.onLogout() }
            )
        }


        composable<Recipes_Categories> {
            val userViewModel: UserViewModel = hiltViewModel()
            val unsplashViewModel: UnsplashViewModel = hiltViewModel()
            val userState by userViewModel.userState.collectAsState()

            // Si el usuario cierra sesión, redirigir a la pantalla de inicio de sesión
            LaunchedEffect(userState) {
                if (userState == null) {
                    navController.navigate(Login) {
                        popUpTo(Home) { inclusive = true } // Limpiar la pila de navegación
                    }
                }
            }

            RecipesCategories_Screen(
                modifier, userViewModel, unsplashViewModel,
                navigateToRecipesList = { name ->
                    navController.navigate(List_Of_Recipes(name = name))
                },
                navigateToHome = { navController.navigate(Home) }
            )
        }

        /* Por ejemplo para la pantalla de la lista de recetas, debemos pasar mas argumentos,
        deben estar todos aqui, y todos luego deben coincidir con los argumentos del propio composable de la vista */
        composable<List_Of_Recipes>
        { backStackEntry ->
            val userViewModel: UserViewModel = hiltViewModel()
            val spoonacularViewModel: SpoonacularViewModel = hiltViewModel()
            //Para poder pasar parametros como argumentos se debe usar el toRoute
            val listOfRecipes: List_Of_Recipes = backStackEntry.toRoute()

            val userState by userViewModel.userState.collectAsState()

            // Si el usuario cierra sesión, redirigir a la pantalla de inicio de sesión
            LaunchedEffect(userState) {
                if (userState == null) {
                    navController.navigate(Login) {
                        popUpTo(Home) { inclusive = true } // Limpiar la pila de navegación
                    }
                }
            }
            ListOfRecipes_Screen(
                modifier,
                listOfRecipes.name,
                userViewModel,
                spoonacularViewModel,
                onRecipeSelected = { navController.navigate(RecipeDetail(it)) },
                navigateToHome = { navController.navigate(Home) })
        }


        /* En este caso pasaremos a la screen un objeto "Receta", para poder hacerlo debemos hacer lo siguiente:
        * - Creamos un Map TypeMap y la pasamos como clave nuestro Modelo de tipo de Receta, que esta en SpoonacularService.Recipe
        * - Le indicamos con To que debe transformarse a RecipeType, los Types se encuentran en "/Core/Navigation/Types" es necesario crearlos para poder pasar los objetos */

        composable<RecipeDetail>(typeMap = mapOf(typeOf<SpoonacularService.Recipe>() to RecipeType)) { backStackEntry ->
            val viewModel: SpoonacularViewModel = hiltViewModel()
            val recipeDetail: RecipeDetail = backStackEntry.toRoute()
            RecipeDetail_Screen(modifier, recipeDetail.recipe, viewModel)
        }

    }
}
