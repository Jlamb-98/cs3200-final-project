package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.models.TrainingPlan
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import com.example.trainingplanner.ui.repositories.UserRepository
import com.example.trainingplanner.ui.repositories.WorkoutsRepository
import java.time.LocalDate

class DashboardScreenState {
    val OFFSET = 1000f   // TODO: does my offset adjust based on screen size??
    val _workouts = mutableStateListOf<Workout>()
    var workouts = mutableListOf<Workout?>()
    var currentWorkout = 0
    var workoutsReady = false

    var username = ""
    var userIsOrganizer = false
    var trainingPlan = TrainingPlan(eventDate = LocalDate.now().toString(), startDate = LocalDate.now().toString())
    var selectedDate: LocalDate = LocalDate.now()

    val translation = Animatable(0f)
}

class DashboardViewModel(application: Application): AndroidViewModel(application) {
    val uiState = DashboardScreenState()

    suspend fun getTrainingPlan() {
        uiState.workoutsReady = false
        println("getting training plan")
        val user = UserRepository.getUser()
        val code = user.trainingPlanId.first()
        uiState.username = user.username!!
        uiState.trainingPlan = TrainingPlanRepository.getTrainingPlan(code!!)
        val startDate = LocalDate.parse(uiState.trainingPlan.startDate)
        if (uiState.selectedDate < startDate) {
            uiState.selectedDate = startDate
        }
        println("Selected Date: ${uiState.selectedDate}")

        // determine if user is Organizer
        val member = uiState.trainingPlan.members.find { it!!.userId == user.id }
        if (member!!.role == "organizer") {
            uiState.userIsOrganizer = true
        }
        println(member)

        // get workouts
        val workouts = uiState.trainingPlan.workouts
        println("getting workouts")
        uiState.workouts = workouts
        val date = uiState.selectedDate
        uiState.currentWorkout = uiState.workouts.indexOfFirst { LocalDate.parse(it?.date) == date }
        uiState.workoutsReady = true
    }

//    fun getCurrentWorkout(offset: Long): Workout {
//        println("getting current workout")
//        val date = uiState.selectedDate.plusDays(offset)
//        return uiState.workouts.find {
//            LocalDate.of(it.year!!, it.month!!, it.day!!) == date
//        } ?: Workout(
//            day = uiState.selectedDate.dayOfMonth,
//            month = uiState.selectedDate.monthValue,
//            year = uiState.selectedDate.year
//        )
//    }

    suspend fun toggleCompletion(workout: Workout) {
        val workoutCopy = workout.copy()
        workoutCopy.membersCompleted.add(UserRepository.getUser().username)
        uiState._workouts[uiState._workouts.indexOf(workout)] = workoutCopy
        TrainingPlanRepository.updateWorkout(workoutCopy)
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