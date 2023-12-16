package com.example.trainingplanner.ui.models

data class TrainingPlan(
    val code: String? = null,
    val eventName: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val eventDate: String? = null,
    val members: MutableList<Member?> = mutableListOf(null),
)
