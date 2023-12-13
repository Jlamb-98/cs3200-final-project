package com.example.trainingplanner.ui.models

import java.time.LocalDate

data class TrainingPlan(
//    val id: String? = null,
    val code: String? = null,
    val eventName: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val eventDate: String? = null,
    val members: MutableList<Member?> = mutableListOf(null),
    val workouts: MutableList<Workout?> = mutableListOf(null),
//    val organizer: Member? = null,
//    val organizerId: String? = null,
//    val name: String? = null,
//    val workoutsPerWeek: Int? = null,
//    val restDaysPerWeek: Int? = null
)
