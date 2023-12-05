package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import java.time.LocalDate

class WorkoutEditorScreenState {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var day by mutableStateOf("")
    var month by mutableStateOf("")
    var year by mutableStateOf("")

    var titleError by mutableStateOf(false)
    var descriptionError by mutableStateOf(false)
    var dayError by mutableStateOf(false)
    var monthError by mutableStateOf(false)
    var yearError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)

    var dayDropdownExpanded by mutableStateOf(false)
    var monthDropdownExpanded by mutableStateOf(false)
    var yearDropdownExpanded by mutableStateOf(false)
}

class WorkoutEditorViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WorkoutEditorScreenState()
    var id: String? = null

    suspend fun setupInitialState(id: String?) {

    }

    suspend fun updateDay(input: String) {

    }

    suspend fun updateMonth(input: String) {

    }

    suspend fun updateYear(input: String) {

    }

    suspend fun saveWorkout() {

    }
}