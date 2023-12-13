package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import java.time.LocalDate

class TrainingPlanEditorScreenState {
    var eventName by mutableStateOf("")
    var description by mutableStateOf("")
    var startDate by mutableStateOf("")
    var eventDate by mutableStateOf("")

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

    suspend fun setupInitialState(code: String?) {
        if (code == null || code == "new") {
            uiState.heading = "Create Training Plan"
            return
        }

        uiState.heading = "Edit Training Plan"
        this.id = code
        val trainingPlan = TrainingPlanRepository.getTrainingPlan(code)
        uiState.description = trainingPlan.description ?: ""
        uiState.eventName = trainingPlan.eventName ?: ""
        uiState.eventDate = trainingPlan.eventDate ?: ""
        uiState.startDate = trainingPlan.startDate ?: ""
    }

    suspend fun saveTrainingPlan() {
//        if (uiState.dayError || uiState.monthError || uiState.yearError) return

        uiState.errorMessage = ""
        uiState.descriptionError = false

        if (uiState.eventName.isEmpty()) {
            uiState.eventNameError = true
            uiState.errorMessage = "Event name cannot be blank"
            return
        }

        if (id == null || id == "new") {    // create new training plan
            TrainingPlanRepository.createTrainingPlan(
                uiState.eventName,
                uiState.description,
                LocalDate.parse(uiState.startDate),   // TODO: read these as LocalDates from input
                LocalDate.parse(uiState.eventDate),
            )
        }
//        else {    // update existing workout
//            val workout = WorkoutsRepository.getWorkouts().find { it.id == id } ?: return
//            WorkoutsRepository.updateWorkout(
//                workout.copy(
//                    title = uiState.title,
//                    description = uiState.description,
//                    day = uiState.day.toInt(),
//                    month = uiState.month.toInt(),
//                    year = uiState.year.toInt()
//                )
//            )
//        }

        uiState.saveSuccess = true
    }
}