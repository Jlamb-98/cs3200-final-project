package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import com.example.trainingplanner.util.exampleTrainingPlan
import java.time.LocalDate

class TrainingPlanEditorScreenState {
    var eventName by mutableStateOf("Utah Valley Half Marathon")
    var description by mutableStateOf("Let's go for sub 2 hours!!")
    var startDate by mutableStateOf("2023-12-15")
    var eventDate by mutableStateOf("2024-02-14")

    // replace with mutableStateOf (DayOfWeek.values().map { DayInfo(it.name) }) in actual use
    var daysOfWeek by mutableStateOf (exampleTrainingPlan())

    var descriptionError by mutableStateOf(false)
    var eventNameError by mutableStateOf(false)
    var eventDateError by mutableStateOf(false)
    var startDateError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)

    var heading by mutableStateOf("")
}

class TrainingPlanEditorViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TrainingPlanEditorScreenState()
    var id: String? = null

    suspend fun saveTrainingPlan() {

        uiState.errorMessage = ""
        uiState.descriptionError = false
        uiState.eventNameError = false
        uiState.eventDateError = false

        if (uiState.eventName.isEmpty()) {
            uiState.eventNameError = true
            uiState.errorMessage = "Event name cannot be blank"
            return
        }
        if (LocalDate.parse(uiState.eventDate).isBefore(LocalDate.parse(uiState.startDate))) {
            uiState.eventDateError = true
            uiState.errorMessage = "Event Date must be after Start Date"
            return
        }

        if (id == null || id == "new") {    // create new training plan
            TrainingPlanRepository.createTrainingPlan(
                uiState.eventName,
                uiState.description,
                LocalDate.parse(uiState.startDate),
                LocalDate.parse(uiState.eventDate),
                uiState.daysOfWeek
            )
        }

        uiState.saveSuccess = true
    }
}