package com.yasserakbbach.noteapp.android.notedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasserakbbach.noteapp.domain.note.Note
import com.yasserakbbach.noteapp.domain.note.NoteDataSource
import com.yasserakbbach.noteapp.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow(SAVED_STATE_NOTE_TITLE_KEY, "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false) // TODO we might need to drop, we have text hint in compose
    private val noteContent = savedStateHandle.getStateFlow(SAVED_STATE_NOTE_CONTENT_KEY, "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false) // TODO we might need to drop, we have text hint in compose
    private val noteColor = savedStateHandle.getStateFlow(
        SAVED_STATE_NOTE_COLOR,
        Note.generateRandomColor()
    )

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor,
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>(SAVED_STATE_NOTE_ID_KEY)?.let { existingNoteId ->
            if(existingNoteId == NON_EXISTING_NOTE_ID) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle[SAVED_STATE_NOTE_TITLE_KEY] = note.title
                    savedStateHandle[SAVED_STATE_NOTE_CONTENT_KEY] = note.content
                    savedStateHandle[SAVED_STATE_NOTE_COLOR] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle[SAVED_STATE_NOTE_TITLE_KEY] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle[SAVED_STATE_NOTE_CONTENT_KEY] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isFocused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

    private companion object {
        const val SAVED_STATE_NOTE_ID_KEY = "noteId"
        const val SAVED_STATE_NOTE_TITLE_KEY = "noteTitle"
        const val SAVED_STATE_NOTE_CONTENT_KEY = "noteContent"
        const val SAVED_STATE_NOTE_COLOR = "noteColor"
        const val NON_EXISTING_NOTE_ID = -1L
    }
}