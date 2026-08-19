package com.example.model

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) = noteDao.insertNote(note)

    suspend fun update(note: Note) = noteDao.updateNote(note)

    suspend fun deleteById(id: Int) = noteDao.deleteNoteById(id)

    suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)
}
