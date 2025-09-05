package ru.kheynov.hamexam.screens.exam_ticket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.kheynov.hamexam.data.CategoriesIntervals
import ru.kheynov.hamexam.data.Questions
import ru.kheynov.hamexam.data.getQuestionsForTicket
import ru.kheynov.hamexam.model.Question

private const val TAG = "ExamTicketVM"

class ExamTicketViewModel(
    private val category: Int,
) : ViewModel() {

    var cursor = 0
        private set

    private val questionsList = mutableListOf<Question>().apply {
        addAll(Questions.tickets.getQuestionsForTicket(category))
    }

    private val _currentQuestion: MutableStateFlow<Question> = MutableStateFlow(questionsList[0])
    val currentQuestion: StateFlow<Question> = _currentQuestion.asStateFlow()

    private val _isNextQuestionAvailable = MutableStateFlow(true)
    val isNextQuestionAvailable: StateFlow<Boolean> = _isNextQuestionAvailable.asStateFlow()

    private val _effects: MutableSharedFlow<ExamScreenEffects> =
        MutableSharedFlow(replay = 1, 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val effects: Flow<ExamScreenEffects> = _effects

    var examTicketState: Array<ExamQuestionState> = Array(questionsList.size) {
        ExamQuestionState(RadioButtonState.Unchecked)
    }

    private val _currentQuestionState = MutableStateFlow<RadioButtonState>(RadioButtonState.Unchecked)
    val currentQuestionState: StateFlow<RadioButtonState> = _currentQuestionState.asStateFlow()

    val questionNumber: Pair<Int, Int>
        get() = cursor + 1 to questionsList.size

    fun nextQuestion() {
        if (cursor < questionsList.size - 1) {
            cursor++
            _currentQuestion.value = questionsList[cursor]
            _isNextQuestionAvailable.value = false
            _currentQuestionState.value = RadioButtonState.Unchecked
            return
        }
        _effects.tryEmit(ExamScreenEffects.ShowEndingDialog)
    }

    fun answerQuestion(answer: Int) {
        _currentQuestionState.update { RadioButtonState.Checked(answer) }
        examTicketState[cursor] = ExamQuestionState(RadioButtonState.Checked(answer))
        _isNextQuestionAvailable.value = true
        Log.i(TAG, examTicketState.toList().toString())
    }

    fun getResults(): Pair<Int, Int> {
        var correct = 0
        val target = CategoriesIntervals.categoriesCorrectToAllCount[category - 1].second
        for (i in 0 until questionsList.size) {
            if (questionsList[i].correct == (examTicketState[i].radioButtonState as
                        RadioButtonState.Checked).answer
            ) correct++
        }
        return correct to target
    }
}

class ExamTicketViewModelFactory(
    private val category: Int,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ExamTicketViewModel(category) as T
}
