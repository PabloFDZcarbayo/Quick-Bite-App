package com.example.quickbite.NavigationGuide.Core.Navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickbite.View.Home_Screen
import com.example.quickbite.View.Login_Screen
import com.example.quickbite.View.RecipiesCategories_Screen


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
        composable<Recipies_Categories> { RecipiesCategories_Screen(modifier) }
    }
}