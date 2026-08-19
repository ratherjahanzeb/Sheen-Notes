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

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as NotesApplication
    val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(app.repository)
    }

    enableEdgeToEdge()
    setContent {
      SheenNotesTheme {
        AppNavigation(viewModel = viewModel)
      }
    }
  }
}

