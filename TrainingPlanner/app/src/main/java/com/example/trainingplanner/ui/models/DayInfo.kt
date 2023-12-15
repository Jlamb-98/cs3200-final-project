package com.example.trainingplanner.ui.models

data class DayInfo(
    val day: String,
    var restDay: Boolean = false,
    var type: String = "",
    var unit: String = "",
    var startAmount: String = "",
    var stepAmount: String = "",
    var stepFrequency: String = ""
)