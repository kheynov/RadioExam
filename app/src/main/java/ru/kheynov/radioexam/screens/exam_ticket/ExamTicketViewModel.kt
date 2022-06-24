@file:Suppress("UNCHECKED_CAST")

package ru.kheynov.radioexam.screens.exam_ticket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kheynov.radioexam.data.Question
import ru.kheynov.radioexam.data.Questions
import ru.kheynov.radioexam.data.getQuestionsForTicket

class ExamTicketViewModel(
    category: Int,
) : ViewModel() {

    private var cursor: Int = 0

    private val questionsList = mutableListOf<Question>()

    var currentQuestion = MutableLiveData<Question>()
        private set

    val questionNumber: Pair<Int, Int>
        get() = cursor + 1 to questionsList.size

    init {
        questionsList.addAll(Questions.tickets.getQuestionsForTicket(category))
        currentQuestion.value = questionsList[0]
    }

    fun nextQuestion() {
        if (cursor < questionsList.size - 1) {
            cursor++
            currentQuestion.value = questionsList[cursor]
        }
    }

    fun previousQuestion() {
        if (cursor > 0) {
            cursor--
            currentQuestion.value = questionsList[cursor]
        }
    }

}

class ExamTicketViewModelFactory(
    private val category: Int,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ExamTicketViewModel(category) as T
}
