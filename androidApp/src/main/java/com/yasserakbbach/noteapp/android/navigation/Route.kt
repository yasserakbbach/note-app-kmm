package com.yasserakbbach.noteapp.android.navigation

sealed class Route(val route: String, ) {
    object NoteList : Route(route = NOTE_LIST_SCREEN_ROUTE)
    object NoteDetail : Route(route = NOTE_DETAIL_SCREEN_ROUTE_WITH_ARG) {
        fun navigateWithNoteId(noteId: Long): String =
            NOTE_DETAIL_SCREEN_ROUTE.plus(noteId)
    }

    companion object {
        const val NOTE_LIST_SCREEN_ROUTE = "note-list"
        const val NOTE_DETAIL_SCREEN_ROUTE_WITH_ARG = "note-detail/{noteId}"
        const val NOTE_DETAIL_SCREEN_ROUTE = "note-detail/"
        const val NOTE_DETAIL_SCREEN_NOTE_ID_ARG_KEY = "noteId"
    }
}