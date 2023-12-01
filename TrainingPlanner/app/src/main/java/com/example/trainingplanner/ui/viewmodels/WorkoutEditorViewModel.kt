package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import java.time.LocalDate

class WorkoutEditorScreenState {
    val title by mutableStateOf("")
    val description by mutableStateOf("")
    val day by mutableStateOf("")
    val month by mutableStateOf("")
    val year by mutableStateOf("")

    val titleError by mutableStateOf(false)
    val descriptionError by mutableStateOf(false)

    val dayDropdownExpanded by mutableStateOf(false)
    val monthDropdownExpanded by mutableStateOf(false)
    val yearDropdownExpanded by mutableStateOf(false)
}

class WorkoutEditorViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WorkoutEditorScreenState()
    var id: String? = null

    suspend fun setupInitialState(id: String?) {

    }


}