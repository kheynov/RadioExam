package ru.kheynov.hamexam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    @SerialName("image_id") val imageId: Int?,
    val correct: Int,
    val text: String,
    val var1: String,
    val var2: String,
    val var3: String,
    val var4: String,
)