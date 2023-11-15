package ru.kheynov.radioexam.screens.exam_ticket

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import ru.kheynov.radioexam.data.CategoriesIntervals
import ru.kheynov.radioexam.screens.NavigationEntries.MENU

private const val TAG = "ExamTicket"

@Composable
fun ExamTicket(
    navController: NavController,
    category: Int,
    viewModel: ExamTicketViewModel = viewModel(factory = ExamTicketViewModelFactory(category)),
) {
    val question = viewModel.currentQuestion.observeAsState()
    val isNextQuestionAvailable = viewModel.isNextQuestionAvailable.observeAsState()
    val showDialog = viewModel.showDialog.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Вопрос №${question.value?.id}",
                modifier = Modifier
                    .weight(10f),
                style = MaterialTheme.typography.h1
            )
            Text(
                text = "${viewModel.questionNumber.first} из ${
                    viewModel.questionNumber
                        .second
                }",
                modifier = Modifier
                    .weight(4f),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.End
            )
        }
        Text(
            text = question.value!!.text,
            style = MaterialTheme.typography.h2
        )
        question.value!!.image_id?.let {
            GlideImage(
                modifier = Modifier.padding(top = 16.dp),
                imageModel = "android.resource://ru.kheynov.radioexam/drawable/exam$it",
                contentScale = ContentScale.FillWidth,
            )
        }

        Log.i(
            TAG, "Recomposition, cursor: ${viewModel.cursor}, radio button state: " +
                    "${viewModel.examTicketState[viewModel.cursor].radioButtonState}"
        )
        RadioButtonBlock(
            modifier = Modifier.padding(top = 16.dp),
            question = question.value!!,
            initialState = remember {
                mutableStateOf(viewModel.examTicketState[viewModel.cursor].radioButtonState)
            }

        ) { answer ->
            viewModel.answerQuestion(answer)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row {
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                    enabled = isNextQuestionAvailable.value == true,
                    onClick = { viewModel.nextQuestion() }) {
                    Text(text = "Далее")
                }
            }
        }
    }

    if (showDialog.value == true) {
        ResultDialog(
            navController = navController,
            results = viewModel.getResults(),
            targetResults = CategoriesIntervals.categoriesCorrectToAllCount[category - 1]
        )
    }
}

@Composable
fun ResultDialog(
    navController: NavController,
    results: Pair<Int, Int>,
    targetResults: Pair<Int, Int>,
) {
    AlertDialog(
        onDismissRequest = {
            navController.navigate(MENU.entry) {
                popUpTo(MENU.entry) { inclusive = true }
            }
        },
        title = { Text(text = "Результаты") },
        text = {
            Text(
                text = "Вы набрали ${results.first} из ${results.second} баллов\nДля " +
                        "сдачи экзамена требуется набрать ${targetResults.first} из ${
                            targetResults.second
                        }\n" +
                        if (results.first < targetResults.first) "Экзамен не сдан" else "Экзамен сдан"
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    navController.navigate(MENU.entry) {
                        popUpTo(MENU.entry) { inclusive = true }
                    }
                },
            ) {
                Text(text = "На главную")
            }
        }
    )
}

