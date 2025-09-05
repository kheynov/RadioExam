package ru.kheynov.hamexam.screens.study

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kheynov.hamexam.data.Questions
import ru.kheynov.hamexam.data.getAllQuestionsForCategory
import ru.kheynov.hamexam.model.Question

class StudyScreenViewModel(
    category: Int,
) : ViewModel() {

    private var cursor = 0

    private val questionsList = mutableListOf<Question>()

    var currentQuestion = MutableLiveData<Question>()
        private set

    val questionNumber: Pair<Int, Int>
        get() = cursor + 1 to questionsList.size

    init {
        questionsList.addAll(Questions.tickets.getAllQuestionsForCategory(category))
        currentQuestion.value = questionsList[0]
    }

    fun nextQuestion() {
        if (cursor < questionsList.size - 1) cursor++
        else cursor = 0
        currentQuestion.value = questionsList[cursor]
    }

    fun previousQuestion() {
        if (cursor > 0) cursor--
        else cursor = questionsList.size - 1
        currentQuestion.value = questionsList[cursor]
    }
}

class StudyScreenViewModelFactory(
    private val category: Int,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        StudyScreenViewModel(category) as T
}