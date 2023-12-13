package com.example.trainingplanner.ui.models

import java.time.LocalDate

data class Workout(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val day: Int? = null,
    val month: Int? = null,
    val year: Int? = null,
    val restDay: Boolean? = null,
    val membersCompleted: List<String?> = mutableListOf(null)   // list of member usernames
//    val userCompleted: Boolean? = null,
) {
    fun getDate(): String {
        return LocalDate.of(year!!, month!!, day!!).toString()
    }
}
