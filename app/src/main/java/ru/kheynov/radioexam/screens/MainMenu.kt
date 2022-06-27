package ru.kheynov.radioexam.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.kheynov.radioexam.screens.NavigationEntries.EXAM
import ru.kheynov.radioexam.screens.NavigationEntries.STUDY

@Composable
fun MainMenu(
    navController: NavHostController,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Сдать экзамен (выберите категорию)",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(onClick = {
                        navController.navigateTo(EXAM.entry, 1)
                    }) {
                        Text(text = "1")
                    }
                    Button(onClick = {
                        navController.navigateTo(EXAM.entry, 2)
                    }) {
                        Text(text = "2")
                    }
                    Button(onClick = {
                        navController.navigateTo(EXAM.entry, 3)
                    }) {
                        Text(text = "3")
                    }
                    Button(onClick = {
                        navController.navigateTo(EXAM.entry, 4)
                    }) {
                        Text(text = "4")
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Посмотреть все билеты (выберите категорию)",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(onClick = {
                        navController.navigateTo(STUDY.entry, 1)
                    }) {
                        Text(text = "1")
                    }
                    Button(onClick = {
                        navController.navigateTo(STUDY.entry, 2)
                    }) {
                        Text(text = "2")
                    }
                    Button(onClick = {
                        navController.navigateTo(STUDY.entry, 3)
                    }) {
                        Text(text = "3")
                    }
                    Button(onClick = {
                        navController.navigateTo(STUDY.entry, 4)
                    }) {
                        Text(text = "4")
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateTo(route: String, category: Int) {
    this.navigate("$route/$category")
}
