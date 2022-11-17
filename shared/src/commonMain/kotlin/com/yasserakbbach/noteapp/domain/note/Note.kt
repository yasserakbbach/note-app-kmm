package com.yasserakbbach.noteapp.domain.note

import com.yasserakbbach.noteapp.presentation.BabyBlueHex
import com.yasserakbbach.noteapp.presentation.LightGreenHex
import com.yasserakbbach.noteapp.presentation.RedOrangeHex
import com.yasserakbbach.noteapp.presentation.RedPinkHex
import com.yasserakbbach.noteapp.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime,
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}
