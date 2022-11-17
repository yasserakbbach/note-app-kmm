package com.yasserakbbach.noteapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform