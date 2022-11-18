package com.yasserakbbach.noteapp.android.notelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.noteapp.domain.note.Note
import com.yasserakbbach.noteapp.domain.note.NoteDataSource
import com.yasserakbbach.noteapp.domain.note.SearchNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchNotesUseCase = SearchNotesUseCase()

    private val notes = savedStateHandle.getStateFlow(SAVED_STATE_NOTES_KEY, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(SAVED_STATE_SEARCH_TEXT_KEY, "")
    private val isSearchActive = savedStateHandle.getStateFlow(SAVED_STATE_IS_SEARCH_ACTIVE_KEY, false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotesUseCase.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_NOTES_KEY] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle[SAVED_STATE_SEARCH_TEXT_KEY] = text
    }

    fun onToggleSearch() {
        savedStateHandle[SAVED_STATE_IS_SEARCH_ACTIVE_KEY] = !isSearchActive.value
        if(!isSearchActive.value) {
            savedStateHandle[SAVED_STATE_SEARCH_TEXT_KEY] = ""
        }
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }

    private companion object {
        const val SAVED_STATE_NOTES_KEY = "notes"
        const val SAVED_STATE_SEARCH_TEXT_KEY = "searchText"
        const val SAVED_STATE_IS_SEARCH_ACTIVE_KEY = "isSearchActive"
    }
}