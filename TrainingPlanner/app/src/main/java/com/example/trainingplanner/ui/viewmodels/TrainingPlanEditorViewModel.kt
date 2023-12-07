package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import com.example.trainingplanner.ui.repositories.UserRepository
import com.example.trainingplanner.ui.repositories.WorkoutsRepository

class TrainingPlanEditorScreenState {
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var eventName by mutableStateOf("")
    var eventDate by mutableStateOf("")
    var startDate by mutableStateOf("")

    var nameError by mutableStateOf(false)
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

    suspend fun setupInitialState(id: String?) {
        if (id == null || id == "new") {
            uiState.heading = "Create Training Plan"
            return
        }

        uiState.heading = "Edit Training Plan"
        this.id = id
        val trainingPlan = TrainingPlanRepository.getTrainingPlan() ?: return
        uiState.name = trainingPlan.name ?: ""
        uiState.description = trainingPlan.description ?: ""
        uiState.eventName = trainingPlan.eventName ?: ""
        uiState.eventDate = trainingPlan.eventDate ?: ""
        uiState.startDate = trainingPlan.startDate ?: ""
    }

    suspend fun saveTrainingPlan() {
//        if (uiState.dayError || uiState.monthError || uiState.yearError) return

        uiState.errorMessage = ""
        uiState.nameError = false
        uiState.descriptionError = false

        if (uiState.name.isEmpty()) {
            uiState.nameError = true
            uiState.errorMessage = "Title cannot be blank"
            return
        }
        if (uiState.description.isEmpty()) {
            uiState.descriptionError = true
            uiState.errorMessage = "Description cannot be blank"
            return
        }

        if (id == null || id == "new") {    // create new training plan
            TrainingPlanRepository.createTrainingPlan(
                uiState.name,
                uiState.description,
                uiState.eventName,
                uiState.eventDate,
                uiState.startDate
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