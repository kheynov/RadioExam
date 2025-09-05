package ru.kheynov.hamexam.screens.exam_ticket

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.kheynov.hamexam.data.CategoriesIntervals
import ru.kheynov.hamexam.screens.NavigationEntries.MENU

private const val TAG = "ExamTicket"

@Composable
fun ExamTicket(
    navController: NavController,
    category: Int,
    viewModel: ExamTicketViewModel = viewModel(factory = ExamTicketViewModelFactory(category)),
) {
    val question = viewModel.currentQuestion.collectAsStateWithLifecycle()
    val isNextQuestionAvailable = viewModel.isNextQuestionAvailable.collectAsStateWithLifecycle()
    val currentQuestionState = viewModel.currentQuestionState.collectAsStateWithLifecycle()

    var shouldShowDialog by remember { mutableStateOf<ExamScreenEffects?>(null) }


    LaunchedEffect(Unit) {
        viewModel.effects.collect {
            Log.i("ExamTicket", "Got effect: $it")
            when (it) {
                ExamScreenEffects.ShowEndingDialog -> shouldShowDialog = it
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
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
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "${viewModel.questionNumber.first} из ${
                        viewModel.questionNumber
                            .second
                    }",
                    modifier = Modifier
                        .weight(4f),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = question.value.text,
                style = MaterialTheme.typography.headlineMedium
            )
            question.value.imageId?.let {
                GlideImage(
                    modifier = Modifier.padding(top = 16.dp),
                    imageModel = { "android.resource://ru.kheynov.hamexam/drawable/exam$it" },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillWidth,
                    )
                )
            }

            Log.i(
                TAG, "Recomposition, cursor: ${viewModel.cursor}, radio button state: " +
                        "${currentQuestionState.value}"
            )
            RadioButtonBlock(
                modifier = Modifier.padding(top = 16.dp),
                question = question.value,
                state = currentQuestionState.value
            ) { answer ->
                viewModel.answerQuestion(answer)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // stick to bottom
                    .padding(16.dp), // margin from edges
                enabled = isNextQuestionAvailable.value,
                onClick = { viewModel.nextQuestion() },
            ) {
                Text(text = "Далее")
            }
        }
    }

    shouldShowDialog?.let {
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

