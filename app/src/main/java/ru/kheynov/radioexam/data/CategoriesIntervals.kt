package ru.kheynov.radioexam.data

object CategoriesIntervals {
    val categoriesTicketsNumbers = listOf(
        (1..34) + (47..98) + (100..135) + (150..226) + (387..391) + (409..422),
        (1..17) + (47..98) + (100..135) + (150..226) + (387..391) + (409..422),
        (1..38) + (47..98) + (100..374) + (387..426),
        (1..426),
    )

    val categoriesCorrectToAllCount = listOf(
        15 to 20,
        20 to 25,
        25 to 30,
        40 to 45,
    )
}