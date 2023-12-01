package com.example.trainingplanner.ui.models

import java.time.LocalDate

data class Workout(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: LocalDate? = null,
    val userCompleted: Boolean? = null,
//    val memberCompletion: List<Boolean?> = mutableListOf(null)
)
