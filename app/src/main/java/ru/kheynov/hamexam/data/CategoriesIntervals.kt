package ru.kheynov.hamexam.data

object CategoriesIntervals {
    val categoriesTicketsNumbers = listOf(
        (1..426),
        (1..38) + (47..98) + (100..374) + (387..426),
        (1..34) + (47..98) + (100..135) + (150..226) + (387..391) + (409..422),
        (1..17) + (47..98) + (100..135) + (150..226) + (387..391) + (409..422),
    )

    val categoriesCorrectToAllCount = listOf(
        40 to 45,
        25 to 30,
        20 to 25,
        15 to 20,
    )
}