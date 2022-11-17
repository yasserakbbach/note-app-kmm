package com.yasserakbbach.noteapp.data.note

import com.yasserakbbach.noteapp.database.NoteDatabase
import com.yasserakbbach.noteapp.domain.note.Note
import com.yasserakbbach.noteapp.domain.note.NoteDataSource
import com.yasserakbbach.noteapp.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(db: NoteDatabase): NoteDataSource {

    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? =
        queries.getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()

    override suspend fun getAllNotes(): List<Note> =
        queries.getAllNotes()
            .executeAsList()
            .map { it.toNote() }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}