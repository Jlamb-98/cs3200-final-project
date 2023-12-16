package com.example.trainingplanner.util

import com.example.trainingplanner.ui.models.DayInfo
import java.time.DayOfWeek

fun exampleTrainingPlan(): List<DayInfo> {
    var daysOfWeek = listOf(
        DayInfo("MONDAY", false, "Strength", "Minutes", "15", "5", "1"),
        DayInfo("TUESDAY", false, "Run", "Miles", "3", "0.5", "1"),
        DayInfo("WEDNESDAY", false, "Run", "Miles", "2", "1", "1"),
        DayInfo("THURSDAY", false, "Run", "Miles", "3", "1", "1"),
        DayInfo("FRIDAY", false, "Cross Train", "Minutes", "30", "15", "1"),
        DayInfo("SATURDAY", false, "Run", "Miles", "4", "2", "1"),
        DayInfo("SUNDAY", true, "Run", "Miles", "2", "2", "1"),
    )

    return daysOfWeek
}