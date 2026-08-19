package com.example

import android.app.Application
import androidx.room.Room
import com.example.model.AppDatabase
import com.example.model.NoteRepository

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
