package com.yasserakbbach.noteapp.android.notelist

import com.yasserakbbach.noteapp.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
