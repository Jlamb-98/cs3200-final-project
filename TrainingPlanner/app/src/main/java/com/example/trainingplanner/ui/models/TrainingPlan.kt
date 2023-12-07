package com.example.trainingplanner.ui.models

import java.time.LocalDate

data class TrainingPlan(
    val id: String? = null,
    val code: String? = null,
    val organizerId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val eventName: String? = null,
    val eventDate: String? = null,
    val startDate: String? = null,
    val members: List<String?> = mutableListOf(null),
    val workouts: List<Workout?> = mutableListOf(null),
//    val workoutsPerWeek: Int? = null,
//    val restDaysPerWeek: Int? = null
)
