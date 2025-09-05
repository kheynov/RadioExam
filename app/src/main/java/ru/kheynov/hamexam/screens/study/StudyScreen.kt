package ru.kheynov.hamexam.screens.study

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skydoves.landscapist.glide.GlideImage
import ru.kheynov.hamexam.ui.theme.Typography


@Composable
fun StudyScreen(
//    navController: NavController,
    category: Int,
    viewModel: StudyScreenViewModel = viewModel(factory = StudyScreenViewModelFactory(category)),
) {
    val question = viewModel.currentQuestion.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
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
                        .weight(8f),
                    style = Typography.headlineLarge
                )
                Text(
                    text = "${viewModel.questionNumber.first} из ${
                        viewModel.questionNumber
                            .second
                    }",
                    modifier = Modifier
                        .weight(5f),
                    style = Typography.headlineMedium,
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = question.value!!.text,
                style = MaterialTheme.typography.headlineMedium
            )
            question.value!!.imageId?.let {
                GlideImage(
                    modifier = Modifier.padding(top = 16.dp),
                    imageModel = { "android.resource://ru.kheynov.radioexam/drawable/exam$it" },
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                question.value?.let {
                    Text(
                        text = "1) ${it.var1}",
                        color = if (it.correct == 1)
                            Color.Red else Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                    Text(
                        text = "2) ${it.var2}",
                        color = if (it.correct == 2)
                            Color.Red else Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                    Text(
                        text = "3) ${it.var3}",
                        color = if (it.correct == 3)
                            Color.Red else Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                    Text(
                        text = "4) ${it.var4}",
                        color = if (it.correct == 4)
                            Color.Red else Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontSize = 18.sp,
                    )
                }
            }

        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    onClick = { viewModel.previousQuestion() }) {
                    Text(text = "Назад")
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    onClick = { viewModel.nextQuestion() }) {
                    Text(text = "Далее")
                }
            }
        }
    }

}
