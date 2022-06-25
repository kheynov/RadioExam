@file:Suppress("UNCHECKED_CAST")

package ru.kheynov.radioexam.screens.exam_ticket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kheynov.radioexam.data.Question
import ru.kheynov.radioexam.data.Questions
import ru.kheynov.radioexam.data.getQuestionsForTicket


private const val TAG = "ExamTicketVM"

class ExamTicketViewModel(
    category: Int,
) : ViewModel() {

    var cursor: Int = 0
        private set

    private val questionsList = mutableListOf<Question>()

    var currentQuestion = MutableLiveData<Question>()
        private set

    val isNextQuestionAvailable = MutableLiveData<Boolean>()

    var examTicketState: Array<ExamQuestionState>
        private set

    val questionNumber: Pair<Int, Int>
        get() = cursor + 1 to questionsList.size

    init {
        questionsList.addAll(Questions.tickets.getQuestionsForTicket(category))
        currentQuestion.value = questionsList[0]
        examTicketState = Array(questionsList.size) {
            ExamQuestionState(RadioButtonState.Unchecked)
        }
    }

    fun nextQuestion() {
        if (cursor < questionsList.size - 1) {
            cursor++
            currentQuestion.value = questionsList[cursor]
            isNextQuestionAvailable.value = false
            return
        }
    }

    fun answerQuestion(answer: Int) {
        examTicketState[cursor] = ExamQuestionState(RadioButtonState.Checked(answer))
        isNextQuestionAvailable.value = true
        Log.i(TAG, examTicketState.toList().toString())
    }
}

class ExamTicketViewModelFactory(
    private val category: Int,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ExamTicketViewModel(category) as T
}
