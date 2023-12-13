package com.example.trainingplanner.ui.models

import java.time.LocalDate

data class Workout(
//    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    val restDay: Boolean? = null,
    val membersCompleted: MutableList<String?> = mutableListOf(null)   // list of usernames, not really dynamic
//    val day: Int? = null,
//    val month: Int? = null,
//    val year: Int? = null,
//    val userCompleted: Boolean? = null,
) {
//    fun getDate(): String {
//        return LocalDate.of(year!!, month!!, day!!).toString()
//    }
}
