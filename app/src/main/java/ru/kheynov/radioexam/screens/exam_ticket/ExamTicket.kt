package ru.kheynov.radioexam.screens.exam_ticket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kheynov.radioexam.components.ExamQuestion

@Composable
fun ExamTicket(
    category: Int,
    viewModel: ExamTicketViewModel = viewModel(factory = ExamTicketViewModelFactory(category)),
) {

    val question = viewModel.currentQuestion.observeAsState()
    ExamQuestion(
        question = question.value!!,
        modifier = Modifier.fillMaxSize(),
        questionNumber = viewModel.questionNumber
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Row {
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = { viewModel.previousQuestion() },
                shape = RoundedCornerShape(1.dp),
            ) {

            }
            Button(modifier = Modifier
                .weight(1f),
                onClick = { viewModel.nextQuestion() }) {
            }
        }
    }
}
