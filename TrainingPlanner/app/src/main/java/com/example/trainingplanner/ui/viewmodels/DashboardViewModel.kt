package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.models.TrainingPlan
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import com.example.trainingplanner.ui.repositories.UserRepository
import com.example.trainingplanner.ui.repositories.WorkoutsRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DashboardScreenState {
    val OFFSET = 1000f   // TODO: does my offset adjust based on screen size??
    val _workouts = mutableStateListOf<Workout>()
    val workouts: List<Workout> get() = _workouts
    var currentWorkout = 0
    var workoutsReady = false

    var username = ""
    var code = ""
    var userIsOrganizer = false
    var trainingPlan = TrainingPlan(eventDate = LocalDate.now().toString(), startDate = LocalDate.now().toString())
    var selectedDate: LocalDate = LocalDate.now()
    var daysUntilEvent: Long = 0

    val translation = Animatable(0f)
}

class DashboardViewModel(application: Application): AndroidViewModel(application) {
    val uiState = DashboardScreenState()

    suspend fun getTrainingPlan() {
        uiState.workoutsReady = false
        println("getting training plan")
        val user = UserRepository.getUser()
        uiState.code = user.trainingPlanCode.first()!!
        uiState.username = user.username!!
        uiState.trainingPlan = TrainingPlanRepository.getTrainingPlan(uiState.code)
        val startDate = LocalDate.parse(uiState.trainingPlan.startDate)
        val eventDate = LocalDate.parse(uiState.trainingPlan.eventDate)
        if (uiState.selectedDate < startDate) {
            uiState.selectedDate = startDate
        }
        uiState.daysUntilEvent = ChronoUnit.DAYS.between(LocalDate.now(), eventDate)

        // determine if user is Organizer
        val member = uiState.trainingPlan.members.find { it!!.userId == user.id }
        if (member!!.role == "organizer") {
            uiState.userIsOrganizer = true
        }

        // get workouts
        println("getting workouts")
        val workouts = WorkoutsRepository.getWorkouts(uiState.code)
        uiState._workouts.clear()
        uiState._workouts.addAll(workouts)
        val date = uiState.selectedDate
        uiState.currentWorkout = uiState.workouts.indexOfFirst { LocalDate.parse(it.date) == date }
        println(uiState.currentWorkout)
        uiState.workoutsReady = true
    }

    suspend fun toggleCompletion(workout: Workout) {
        val username = UserRepository.getUser().username
        val workoutCopy = workout.copy()
        if (workout.membersCompleted.contains(username)) {
            workoutCopy.membersCompleted.remove(username)
        } else {
            workoutCopy.membersCompleted.add(username)
        }
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
            uiState.selectedDate = uiState.selectedDate.minusDays(1)
            uiState.translation.snapTo(0f)
        } else if (uiState.translation.value <= -uiState.OFFSET) {
            uiState.currentWorkout++
            uiState.selectedDate = uiState.selectedDate.plusDays(1)
            uiState.translation.snapTo(0f)
        }
        println("Selected Date: ${uiState.selectedDate}")
    }

    suspend fun animateToCenter() {
        var snapValue = 0f
        if (uiState.translation.value > uiState.OFFSET / 2) {
            if (uiState.selectedDate > LocalDate.parse(uiState.trainingPlan.startDate)) {
                snapValue = uiState.OFFSET
            }
        } else if (uiState.translation.value < -uiState.OFFSET / 2) {
            if (uiState.selectedDate < LocalDate.parse(uiState.trainingPlan.eventDate)) {
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