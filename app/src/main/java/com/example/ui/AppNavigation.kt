package com.example.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object NotesRoute

@Serializable
data class AddNoteRoute(val noteId: Int = -1)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(viewModel: NotesViewModel) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = NotesRoute) {
            composable<NotesRoute> {
                NotesScreen(
                    viewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onAddNoteClick = {
                        navController.navigate(AddNoteRoute())
                    },
                    onNoteClick = { noteId ->
                        navController.navigate(AddNoteRoute(noteId = noteId))
                    }
                )
            }
            composable<AddNoteRoute> { backStackEntry ->
                val route: AddNoteRoute = backStackEntry.toRoute()
                AddNoteScreen(
                    viewModel = viewModel,
                    noteId = route.noteId,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
