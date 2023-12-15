package com.example.trainingplanner.util

import java.time.LocalDate

fun reformatDate(date: String): String {
    return when (val parsedDate = LocalDate.parse(date)) {
        LocalDate.now().minusDays(1) -> "Yesterday"
        LocalDate.now() -> "Today"
        LocalDate.now().plusDays(1) -> "Tomorrow"
        else -> "${parsedDate.dayOfWeek}, ${parsedDate.month} ${parsedDate.dayOfMonth}, ${parsedDate.year}"
    }
}