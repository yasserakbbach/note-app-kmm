package com.yasserakbbach.noteapp.android.notedetail

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleHintVisible: Boolean = false, // TODO we might need to drop, we have text hint in compose
    val noteContent: String = "",
    val isNoteContentHintVisible: Boolean = false, // TODO we might need to drop, we have text hint in compose
    val noteColor: Long = 0xFFFFFF,
)
