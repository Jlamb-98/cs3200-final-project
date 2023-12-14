package com.example.trainingplanner.ui.models

data class User(
    val id: String? = null,
    val username: String? = null,
    val trainingPlanCode: MutableList<String?> = mutableListOf(), // could be a list of id's
//    val role: String? = null,
    // photo, first name
)
