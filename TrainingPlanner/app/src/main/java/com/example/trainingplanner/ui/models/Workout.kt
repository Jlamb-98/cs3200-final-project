package com.example.trainingplanner.ui.models

data class Workout(
    val id: String? = null,
    val trainingPlanCode: String? = null,
    val date: String? = null,
    val amount: Float? = null,
    val unit: String? = null,
    val type: String? = null,
    val description: String? = null,
    val restDay: Boolean? = null,
    val membersCompleted: MutableList<String?> = mutableListOf()   // list of usernames, not really dynamic
)
