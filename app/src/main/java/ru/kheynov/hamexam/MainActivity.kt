package ru.kheynov.hamexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.kheynov.hamexam.data.Questions
import ru.kheynov.hamexam.screens.MainMenu
import ru.kheynov.hamexam.screens.NavigationEntries.EXAM
import ru.kheynov.hamexam.screens.NavigationEntries.MENU
import ru.kheynov.hamexam.screens.NavigationEntries.STUDY
import ru.kheynov.hamexam.screens.exam_ticket.ExamTicket
import ru.kheynov.hamexam.screens.study.StudyScreen
import ru.kheynov.hamexam.ui.theme.HamExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true // dark icons

        setContent {
            Questions.init(context = applicationContext)
            HamExamTheme {
                Scaffold(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.systemBars,
                    )
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        navController = navController,
                        startDestination = MENU.entry
                    ) {
                        composable(
                            EXAM.entry + "/{category}",
                            arguments = listOf(navArgument("category") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            ExamTicket(
                                navController = navController,
                                category = backStackEntry.arguments?.getInt("category")!!
                            )
                        }

                        composable(MENU.entry) {
                            MainMenu(navController = navController)
                        }

                        composable(
                            STUDY.entry + "/{category}",
                            arguments = listOf(navArgument("category") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            StudyScreen(
//                            navController = navController,
                                category = backStackEntry.arguments?.getInt("category")!!
                            )
                        }
                    }
                }
            }
        }
    }
}
