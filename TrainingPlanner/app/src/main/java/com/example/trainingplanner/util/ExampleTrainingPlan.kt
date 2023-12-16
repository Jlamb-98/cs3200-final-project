package com.example.trainingplanner.util

import com.example.trainingplanner.ui.models.DayInfo

fun exampleTrainingPlan(): List<DayInfo> {
    return listOf(
        DayInfo("MONDAY", false, "Strength", "Minutes", "15", "5", "1"),
        DayInfo("TUESDAY", false, "Run", "Mile", "3", "0.5", "1"),
        DayInfo("WEDNESDAY", false, "Run", "Mile", "2", "1", "1"),
        DayInfo("THURSDAY", false, "Run", "Mile", "3", "1", "1"),
        DayInfo("FRIDAY", false, "Cross Train", "Minutes", "30", "15", "1"),
        DayInfo("SATURDAY", false, "Run", "Mile", "4", "2", "1"),
        DayInfo("SUNDAY", true, "Run", "Mile", "2", "2", "1"),
    )
}