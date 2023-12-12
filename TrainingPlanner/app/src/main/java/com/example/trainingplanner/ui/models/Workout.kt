package com.example.trainingplanner.ui.models

import java.time.LocalDate

data class Workout(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val day: Int? = null,
    val month: Int? = null,
    val year: Int? = null,
    val created: Boolean? = false,
    val userCompleted: Boolean? = null,
//    val memberCompletion: List<Boolean?> = mutableListOf(null)
) {
    fun getDate(): String {
        return LocalDate.of(year!!, month!!, day!!).toString()
    }
}
