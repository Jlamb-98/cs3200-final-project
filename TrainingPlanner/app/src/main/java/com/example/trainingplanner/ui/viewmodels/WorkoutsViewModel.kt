package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.repositories.WorkoutsRepository

class WorkoutsScreenState {
    val _workouts = mutableStateListOf<Workout>()
    val workouts: List<Workout> get() = _workouts
}

class WorkoutsViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WorkoutsScreenState()

    suspend fun getWorkouts() {
        val workouts = WorkoutsRepository.getWorkouts()
        uiState._workouts.clear()
        uiState._workouts.addAll(workouts)
    }
}