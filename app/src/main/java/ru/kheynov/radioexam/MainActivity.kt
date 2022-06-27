package ru.kheynov.radioexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.kheynov.radioexam.data.Questions
import ru.kheynov.radioexam.screens.MainMenu
import ru.kheynov.radioexam.screens.NavigationEntries.*
import ru.kheynov.radioexam.screens.exam_ticket.ExamTicket
import ru.kheynov.radioexam.screens.study.StudyScreen
import ru.kheynov.radioexam.ui.theme.RadioExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Questions.init(context = applicationContext)
            RadioExamTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = MENU.entry) {
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RadioExamTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }
    }
}