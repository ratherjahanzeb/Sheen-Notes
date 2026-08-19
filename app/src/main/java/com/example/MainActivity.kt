package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.NotesApplication
import com.example.ui.AppNavigation
import com.example.ui.NotesViewModel
import com.example.ui.NotesViewModelFactory
import com.example.ui.theme.SheenNotesTheme

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import com.example.dataStore

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as NotesApplication
    val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(app.repository, app.dataStore)
    }

    enableEdgeToEdge()
    setContent {
      val isDarkModePref by viewModel.isDarkMode.collectAsStateWithLifecycle()
      val darkTheme = isDarkModePref ?: isSystemInDarkTheme()

      SheenNotesTheme(darkTheme = darkTheme) {
        AppNavigation(viewModel = viewModel)
      }
    }
  }
}

