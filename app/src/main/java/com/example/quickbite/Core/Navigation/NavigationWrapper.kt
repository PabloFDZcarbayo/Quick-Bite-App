package com.example.quickbite.Core.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickbite.View.Home_Screen
import com.example.quickbite.View.Login_Screen
import com.example.quickbite.View.RecipiesCategories_Screen
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
            Home_Screen(modifier) {navController.navigate(Recipies_Categories) }
        }
        composable<Recipies_Categories> {
            val viewModel: UnsplashViewModel = hiltViewModel()
            RecipiesCategories_Screen(modifier, viewModel) }
    }
}