package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.repositories.WorkoutsRepository
import java.time.LocalDate

class DashboardScreenState {
    val OFFSET = 1000f   // TODO: does my offset adjust based on screen size??
    val _workouts = mutableStateListOf<Workout>()
    val workouts: List<Workout> get() = _workouts
    var numWorkouts = 0
    var currentWorkout = 0
    var selectedDate: LocalDate = LocalDate.now()

    val translation = Animatable(0f)
}

class DashboardViewModel(application: Application): AndroidViewModel(application) {
    val uiState = DashboardScreenState()

    suspend fun getWorkouts() {
        val workouts = WorkoutsRepository.getWorkouts()
        println("getting workouts")
        uiState._workouts.clear()
        uiState._workouts.addAll(workouts)
        uiState.numWorkouts = uiState.workouts.size
    }

    fun getCurrentWorkout(offset: Long): Workout {
        println("getting current workout")
        val date = uiState.selectedDate.plusDays(offset)
        return uiState.workouts.find {
            LocalDate.of(it.year!!, it.month!!, it.day!!) == date
        } ?: Workout(
            day = uiState.selectedDate.dayOfMonth,
            month = uiState.selectedDate.monthValue,
            year = uiState.selectedDate.year
        )
    }

    suspend fun toggleCompletion(workout: Workout) {
        val workoutCopy = workout.copy(userCompleted = !(workout.userCompleted ?: false))
        uiState._workouts[uiState._workouts.indexOf(workout)] = workoutCopy
        WorkoutsRepository.updateWorkout(workoutCopy)
    }

    suspend fun dragPage(change: Float) {
        uiState.translation.snapTo(
            uiState.translation.value + change
        )
    }

    suspend fun updateCurrentWorkout() {
        if (uiState.translation.value >= uiState.OFFSET) {
            uiState.currentWorkout--
            uiState.selectedDate.minusDays(1)
            uiState.translation.snapTo(0f)
        } else if (uiState.translation.value <= -uiState.OFFSET) {
            uiState.currentWorkout++
            uiState.selectedDate.plusDays(1)
            uiState.translation.snapTo(0f)
        }
    }

    suspend fun animateToCenter() {
        var snapValue = 0f
        if (uiState.translation.value > uiState.OFFSET / 2) {
            if (uiState.currentWorkout > 0) {
                snapValue = uiState.OFFSET
            }
        } else if (uiState.translation.value < -uiState.OFFSET / 2) {
            if (uiState.currentWorkout < uiState.numWorkouts - 1) {
                snapValue = -uiState.OFFSET
            }
        }
        uiState.translation.animateTo(
            snapValue,
            animationSpec = spring(
                dampingRatio = .9f,
                stiffness = 250f
            ),
        )
    }
}