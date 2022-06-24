package ru.kheynov.radioexam.data

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.kheynov.radioexam.R

object Tickets {
    private lateinit var jsonList: List<Ticket>
    val tickets: List<Ticket>
        get() = jsonList


    fun init(context: Context) {
        val text =
            context.resources.openRawResource(R.raw.exam).bufferedReader().use { it.readText() }
        jsonList = Json.decodeFromString<List<Ticket>>(text).sortedBy { it.id }
    }
}

fun List<Ticket>.getTicket(id: Int): Ticket? {
    return this.find { it.id == id }
}