package ru.kheynov.radioexam.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.kheynov.radioexam.data.Question

@Composable
fun ExamQuestion(
    modifier: Modifier = Modifier,
    question: Question,
    questionNumber: Pair<Int, Int>,
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Вопрос №${question.id}",
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .weight(10f),
                        style = MaterialTheme.typography.h1
                    )
                    Text(text = "${questionNumber.first} из ${questionNumber.second}",
                        modifier = Modifier.weight(4f),
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.End)
                }
                Text(
                    text = question.text,
                    style = MaterialTheme.typography.h2
                )
                question.image_id?.let {
                    GlideImage(
                        modifier = Modifier.padding(top = 16.dp),
                        imageModel = "android.resource://ru.kheynov.radioexam/drawable/exam$it",
                        contentScale = ContentScale.FillWidth,
                    )
                }
                val radioButtonState: MutableState<RadioButtonState> =
                    remember { mutableStateOf(RadioButtonState.Unchecked) }
                RadioButtonBlock(
                    modifier = Modifier.padding(top = 16.dp),
                    question = question,
                    state = radioButtonState.value,
                    onSelect = { radioButtonState.value = RadioButtonState.Checked(it) }
                )
            }
        }
    }
}

@Composable
fun RadioButtonBlock(
    question: Question,
    modifier: Modifier,
    state: RadioButtonState,
    onSelect: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        TestChoice(
            state = state is RadioButtonState.Checked && state.count == 1,
            onClick = { onSelect(1) },
            text = question.var1
        )
        TestChoice(
            state = state is RadioButtonState.Checked && state.count == 2,
            onClick = { onSelect(2) },
            text = question.var2
        )
        TestChoice(
            state = state is RadioButtonState.Checked && state.count == 3,
            onClick = { onSelect(3) },
            text = question.var3
        )
        TestChoice(
            state = state is RadioButtonState.Checked && state.count == 4,
            onClick = { onSelect(4) },
            text = question.var4
        )
    }
}

@Composable
fun TestChoice(
    state: Boolean,
    onClick: () -> Unit,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        RadioButton(
            selected = state,
            onClick = onClick
        )
        Text(text = text)
    }
}

sealed interface RadioButtonState {
    object Unchecked : RadioButtonState
    data class Checked(val count: Int) : RadioButtonState
}

@Preview(showBackground = true)
@Composable
fun ExamQuestionPreview() {
    ExamQuestion(
        question = Question(
            23,
            2,
            3,
            "Чем в основном определяется коэффициент усиления схемы с применением операционного усилителя?",
            "Типом операционного усилителя.",
            "Глубиной частотной коррекции.",
            "Глубиной отрицательной обратной связи, задаваемой внешними элементами.",
            "Напряжением питания операционного усилителя.",
        ),
        questionNumber = 15 to 20
    )
}