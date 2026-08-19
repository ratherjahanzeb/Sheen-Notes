package com.example.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object NotesRoute

@Serializable
object AddNoteRoute

@Composable
fun AppNavigation(viewModel: NotesViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NotesRoute) {
        composable<NotesRoute> {
            NotesScreen(
                viewModel = viewModel,
                onAddNoteClick = {
                    navController.navigate(AddNoteRoute)
                }
            )
        }
        composable<AddNoteRoute> {
            AddNoteScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
