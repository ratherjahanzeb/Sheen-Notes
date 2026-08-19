package com.example
import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.model.AppDatabase
import com.example.model.NoteRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class NotesApplication : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var repository: NoteRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "notes_database"
        ).build()
        repository = NoteRepository(database.noteDao())
    }
}
