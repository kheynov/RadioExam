package ru.kheynov.radioexam.screens.exam_ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kheynov.radioexam.data.Question

@Composable
fun RadioButtonBlock(
    question: Question,
    modifier: Modifier,
    initialState: MutableState<RadioButtonState>,
    onSelect: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        TestChoice(
            state = initialState.value is RadioButtonState.Checked && (initialState.value as RadioButtonState
            .Checked).answer == 1,
            onClick = {
                initialState.value = RadioButtonState.Checked(1)
                onSelect(1)
            },
            text = question.var1
        )
        TestChoice(
            state = initialState.value is RadioButtonState.Checked && (initialState.value as RadioButtonState
            .Checked).answer == 2,
            onClick = {
                initialState.value = RadioButtonState.Checked(2)
                onSelect(2)
            },
            text = question.var2
        )
        TestChoice(
            state = initialState.value is RadioButtonState.Checked && (initialState.value as RadioButtonState
            .Checked).answer == 3,
            onClick = {
                initialState.value = RadioButtonState.Checked(3)
                onSelect(3)
            },
            text = question.var3
        )
        TestChoice(
            state = initialState.value is RadioButtonState.Checked && (initialState.value as RadioButtonState.Checked).answer == 4,
            onClick = {
                initialState.value = RadioButtonState.Checked(4)
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
