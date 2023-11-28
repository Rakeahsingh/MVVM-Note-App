package com.example.mvvmnoteapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmnoteapp.core.navigation.Route
import com.example.mvvmnoteapp.feature_note.presentation.addNoteScreen.AddNoteScreen
import com.example.mvvmnoteapp.feature_note.presentation.noteScreen.NoteScreen
import com.example.mvvmnoteapp.feature_note.presentation.splashScreen.SplashScreen
import com.example.mvvmnoteapp.ui.theme.MVVMNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMNoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState
                    ) {
                        NavHost(navController = navController, startDestination = Route.SplashScreen){
                            composable(Route.SplashScreen){
                                SplashScreen(onNavigate = {
                                    navController.navigate(it.route)
                                })
                            }

                            composable(Route.NoteScreen){
                                NoteScreen(onNavigate = {
                                    navController.navigate(it.route)
                                },
                                    scaffoldState = scaffoldState
                                    )
                            }

                            composable(
                                route = Route.AddNoteScreen + "/{noteId}/{noteColor}",
                                arguments = listOf(
                                    navArgument("noteId"){
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument("noteColor"){
                                        type = NavType.IntType
                                        defaultValue = -1
                                    }
                                )
                            ){
                                val color = it.arguments?.getInt("noteColor") ?: -1

                                AddNoteScreen(
                                        scaffoldState = scaffoldState,
                                        onNavigateUp = {
                                            navController.navigateUp()
                                        },
                                        noteColor = color
                                )

                            }

                        }
                    }
                }
            }
        }
    }
}

