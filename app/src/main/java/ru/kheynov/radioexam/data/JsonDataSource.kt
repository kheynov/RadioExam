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
        jsonList = Json.decodeFromString(text)
    }
    val images = hashMapOf(
        1 to R.drawable.exam1,
        2 to R.drawable.exam2,
        3 to R.drawable.exam3,
        4 to R.drawable.exam4,
        5 to R.drawable.exam5,
        6 to R.drawable.exam6,
        7 to R.drawable.exam7,
        8 to R.drawable.exam8,
        9 to R.drawable.exam9,
        10 to R.drawable.exam10,
        11 to R.drawable.exam11,
        12 to R.drawable.exam12,
        13 to R.drawable.exam13,
        14 to R.drawable.exam14,
        15 to R.drawable.exam15,
        16 to R.drawable.exam16,
        17 to R.drawable.exam17,
        18 to R.drawable.exam18,
    )
}