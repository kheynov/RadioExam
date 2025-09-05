package ru.kheynov.hamexam.screens.exam_ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kheynov.hamexam.model.Question

@Composable
fun RadioButtonBlock(
    question: Question,
    modifier: Modifier,
    state: RadioButtonState,
    onSelect: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        TestChoice(
            state = state is RadioButtonState.Checked && state.answer == 1,
            onClick = {
                onSelect(1)
            },
            text = question.var1
        )
        TestChoice(
            state = state is RadioButtonState.Checked && state.answer == 2,
            onClick = {
                onSelect(2)
            },
            text = question.var2
        )
        TestChoice(
            state = state is RadioButtonState.Checked && state.answer == 3,
            onClick = {
                onSelect(3)
            },
            text = question.var3
        )
        TestChoice(
            state = state is RadioButtonState.Checked && state.answer == 4,
            onClick = {
                onSelect(4)
            },
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
    data class Checked(val answer: Int) : RadioButtonState
}
