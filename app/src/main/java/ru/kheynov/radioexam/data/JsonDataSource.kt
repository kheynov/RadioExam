package ru.kheynov.radioexam.data

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.kheynov.radioexam.R

object Questions {
    private lateinit var jsonList: List<Question>
    val tickets: List<Question>
        get() = jsonList


    fun init(context: Context) {
        val text =
            context.resources.openRawResource(R.raw.exam).bufferedReader().use { it.readText() }
        jsonList = Json.decodeFromString<List<Question>>(text).sortedBy { it.id }
    }
}

fun List<Question>.getQuestion(id: Int): Question? {
    return this.find { it.id == id }
}

fun List<Question>.getQuestionsForTicket(category: Int): List<Question> {
    val resultList = mutableListOf<Question>()
    CategoriesIntervals.categoriesTicketsNumbers[category - 1].forEach {
        resultList.add(this.getQuestion(it)!!)
    }

    return resultList.shuffled()
        .take(CategoriesIntervals.categoriesCorrectToAllCount[category - 1].second)
}