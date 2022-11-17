package com.yasserakbbach.noteapp.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.yasserakbbach.noteapp.database.NoteDatabase

actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = NoteDatabase.Schema,
            context = context,
            name = "note.db",
        )
}