package com.example.trainingplanner.ui.models

data class User(
    val id: String? = null,
    val name: String? = null,
    val trainingPlanId: String? = "none",
    val role: String? = "none"
)
