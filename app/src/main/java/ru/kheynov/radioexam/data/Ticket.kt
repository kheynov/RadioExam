package ru.kheynov.radioexam.data

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: Int,
    val image_id: Int?,
    val correct: String,
    val text: String,
    val var1: String,
    val var2: String,
    val var3: String,
    val var4: String,
)