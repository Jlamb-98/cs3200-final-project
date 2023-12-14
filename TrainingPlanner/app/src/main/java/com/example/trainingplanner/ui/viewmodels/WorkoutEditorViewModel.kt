package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import com.example.trainingplanner.ui.repositories.WorkoutsRepository
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class WorkoutEditorScreenState {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var day by mutableStateOf("")
    var month by mutableStateOf("")
    var year by mutableStateOf("")
    var restDay by mutableStateOf(false)

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

    var heading by mutableStateOf("")
}

class WorkoutEditorViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WorkoutEditorScreenState()
    private var date: String? = null
    private var code: String? = null

    suspend fun setupInitialState(date: String?, code: String?) {
        if (date == null || date == "new" || code == null) {
            uiState.heading = "Create Workout"
            return
        }

        uiState.heading = "Edit Workout"
        this.date = date
        this.code = code
        val workout = TrainingPlanRepository.getTrainingPlan(code).workouts.find { it!!.date == date } ?: return
        uiState.title = workout.title ?: ""
        uiState.description = workout.description ?: ""
        uiState.day = "${LocalDate.parse(workout.date).dayOfMonth}" // TODO: fix these dumb conversions
        uiState.month = "${LocalDate.parse(workout.date).monthValue}"
        uiState.year = "${LocalDate.parse(workout.date).year}"
        uiState.restDay = workout.restDay ?: false
    }

    fun updateDay(input: String) {
        uiState.dayError = false
        uiState.errorMessage = ""
        try {
            input.filter { !it.isWhitespace() }.toInt()
        } catch (e: Exception) {
            uiState.dayError = true
            uiState.errorMessage = "Day must be a number"
        } finally {
            uiState.day = input.filter { !it.isWhitespace() }
        }
    }

    fun updateMonth(input: String) {
        uiState.monthError = false
        uiState.errorMessage = ""
        try {
            input.filter { !it.isWhitespace() }.toInt()
        } catch (e: Exception) {
            uiState.monthError = true
            uiState.errorMessage = "Month must be a number"
        } finally {
            uiState.month = input.filter { !it.isWhitespace() }
        }
    }

    fun updateYear(input: String) {
        uiState.yearError = false
        uiState.errorMessage = ""
        try {
            input.filter { !it.isWhitespace() }.toInt()
        } catch (e: Exception) {
            uiState.yearError = true
            uiState.errorMessage = "Year must be a number"
        } finally {
            uiState.year = input.filter { !it.isWhitespace() }
        }
    }

    suspend fun saveWorkout() {
        if (uiState.dayError || uiState.monthError || uiState.yearError) return

        uiState.errorMessage = ""
        uiState.titleError = false
        uiState.descriptionError = false

        if (uiState.title.isEmpty()) {
            uiState.titleError = true
            uiState.errorMessage = "Title cannot be blank"
            return
        }
        if (uiState.description.isEmpty()) {
            uiState.descriptionError = true
            uiState.errorMessage = "Description cannot be blank"
            return
        }

        val workout = TrainingPlanRepository.getTrainingPlan(code!!).workouts.find { it!!.date == date } ?: return
        TrainingPlanRepository.updateWorkout(
            workout.copy(
                title = uiState.title,
                description = uiState.description,
                date = "${uiState.year}-${uiState.month}-${uiState.day}",
                restDay = uiState.restDay
            )
        )

        uiState.saveSuccess = true
    }

//    private fun timestampToLocalDate(timestamp: Timestamp): LocalDate {
//        val instant: Instant = timestamp.toDate().toInstant()
//        return instant.atZone(ZoneId.systemDefault()).toLocalDate()
//    }
//
//    private fun createTimestamp(year: Int, month: Int, day: Int): Timestamp {
//        val localDate = LocalDate.of(year, month, day)
//        val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
//        return Timestamp(Date.from(instant))
//    }
}