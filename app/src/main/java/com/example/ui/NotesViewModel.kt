package com.example.ui

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.model.Note
import com.example.model.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val THEME_KEY = booleanPreferencesKey("is_dark_mode")

    val isDarkMode: StateFlow<Boolean?> = dataStore.data
        .map { preferences -> preferences[THEME_KEY] }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleTheme() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                val current = preferences[THEME_KEY] ?: false
                preferences[THEME_KEY] = !current
            }
        }
    }

    val uiState: StateFlow<List<Note>> = repository.allNotes
        .combine(_searchQuery) { notes, query ->
            if (query.isBlank()) {
                notes
            } else {
                notes.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    it.content.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNote(title: String, content: String) {
        val timestamp = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault()).format(java.util.Date())
        viewModelScope.launch {
            repository.insert(Note(title = title, content = content, timestamp = timestamp))
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        val timestamp = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault()).format(java.util.Date())
        viewModelScope.launch {
            repository.update(Note(id = id, title = title, content = content, timestamp = timestamp))
        }
    }

    fun deleteNoteById(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }

    suspend fun getNoteById(id: Int): Note? {
        return repository.getNoteById(id)
    }
}

class NotesViewModelFactory(
    private val repository: NoteRepository,
    private val dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository, dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
