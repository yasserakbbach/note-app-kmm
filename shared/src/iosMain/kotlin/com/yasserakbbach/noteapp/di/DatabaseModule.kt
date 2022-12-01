package com.yasserakbbach.noteapp.di

import com.yasserakbbach.noteapp.data.local.DatabaseDriverFactory
import com.yasserakbbach.noteapp.data.note.SqlDelightNoteDataSource
import com.yasserakbbach.noteapp.database.NoteDatabase
import com.yasserakbbach.noteapp.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}