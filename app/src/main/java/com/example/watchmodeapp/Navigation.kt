package com.example.watchmodeapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watchmodeapp.presentation.TitleDetails.TitleDetailsScreen
import com.example.watchmodeapp.presentation.TitleDetails.TitleDetailsViewModel
import com.example.watchmodeapp.presentation.TitlesList.TitlesListViewModel
import com.example.watchmodeapp.presentation.TitlesListScreen

@Composable
fun Navigation() {
    val controller = rememberNavController()

    NavHost(navController = controller, startDestination = "home") {
        composable("home") {
            val vm = hiltViewModel<TitlesListViewModel>()
            TitlesListScreen(vm) {
                controller.navigate("titleDetails/$it")
            }
        }
        composable(
            "titleDetails/{titleId}",
            arguments = listOf(
                navArgument("titleId") { type = androidx.navigation.NavType.IntType },
            )
        ) { navBackStackEntry ->
            val titleId = navBackStackEntry.arguments?.getInt("titleId") ?: 0
            val vm = hiltViewModel<TitleDetailsViewModel>()
            TitleDetailsScreen(vm, titleId) {
                controller.popBackStack()
            }
        }
    }
}