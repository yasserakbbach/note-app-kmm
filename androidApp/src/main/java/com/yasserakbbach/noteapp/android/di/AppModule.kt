package com.yasserakbbach.noteapp.android.di

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import com.yasserakbbach.noteapp.data.local.DatabaseDriverFactory
import com.yasserakbbach.noteapp.data.note.SqlDelightNoteDataSource
import com.yasserakbbach.noteapp.database.NoteDatabase
import com.yasserakbbach.noteapp.domain.note.NoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver =
        DatabaseDriverFactory(app).createDriver()

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource =
        SqlDelightNoteDataSource(NoteDatabase(driver))
}