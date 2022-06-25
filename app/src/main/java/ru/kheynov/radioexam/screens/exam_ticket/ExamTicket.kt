package ru.kheynov.radioexam.screens.exam_ticket

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.glide.GlideImage
import ru.kheynov.radioexam.components.RadioButtonBlock

private const val TAG = "ExamTicket"

@Composable
fun ExamTicket(
    category: Int,
    viewModel: ExamTicketViewModel = viewModel(factory = ExamTicketViewModelFactory(category)),
) {

    val question = viewModel.currentQuestion.observeAsState()
    val isNextQuestionAvailable = viewModel.isNextQuestionAvailable.observeAsState()

    LazyColumn(Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Вопрос №${question.value?.id}",
                    modifier = Modifier
                        .weight(10f),
                    style = MaterialTheme.typography.h1
                )
                Text(text = "${viewModel.questionNumber.first} из ${
                    viewModel.questionNumber
                        .second
                }",
                    modifier = Modifier
                        .weight(4f),
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.End)
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

            Log.i(TAG, "Recomposition, cursor: ${viewModel.cursor}, radio button state: " +
                    "${viewModel.examTicketState[viewModel.cursor].radioButtonState}")
            RadioButtonBlock(
                modifier = Modifier.padding(top = 16.dp),
                question = question.value!!,
                initialState = mutableStateOf(viewModel.examTicketState[viewModel.cursor]
                    .radioButtonState)
            ) { answer ->
                viewModel.answerQuestion(answer)
            }

        }
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
