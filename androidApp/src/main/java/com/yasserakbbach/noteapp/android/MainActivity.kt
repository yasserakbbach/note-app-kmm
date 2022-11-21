package com.yasserakbbach.noteapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yasserakbbach.noteapp.android.navigation.Route
import com.yasserakbbach.noteapp.android.notedetail.NoteDetailScreen
import com.yasserakbbach.noteapp.android.notelist.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Route.NoteList.route) {
                composable(
                    route = Route.NoteList.route,
                ) {
                    NoteListScreen(navController = navController)
                }
                composable(
                    route = Route.NoteDetail.route,
                    arguments = listOf(
                        navArgument(Route.NOTE_DETAIL_SCREEN_NOTE_ID_ARG_KEY) {
                            type = NavType.LongType
                            defaultValue = -1L
                        })
                ) {
                    NoteDetailScreen(navController = navController)
                }
            }
        }
    }
}
