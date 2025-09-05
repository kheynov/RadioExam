package ru.kheynov.hamexam.screens.exam_ticket

sealed interface ExamScreenEffects {
    data object ShowEndingDialog: ExamScreenEffects
}