package com.yasserakbbach.noteapp.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.yasserakbbach.noteapp.database.NoteDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = NoteDatabase.Schema,
            name = "note.db",
        )
}