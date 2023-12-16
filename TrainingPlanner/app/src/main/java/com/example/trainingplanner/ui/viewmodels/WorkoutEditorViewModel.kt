package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.repositories.WorkoutsRepository
import java.time.LocalDate

class WorkoutEditorScreenState {
    var day by mutableStateOf("")
    var month by mutableStateOf("")
    var year by mutableStateOf("")
    var amount by mutableStateOf("")
    var unit by mutableStateOf("")
    var type by mutableStateOf("")
    var description by mutableStateOf("")
    var restDay by mutableStateOf(false)

    var dayError by mutableStateOf(false)
    var monthError by mutableStateOf(false)
    var yearError by mutableStateOf(false)
    var amountError by mutableStateOf(false)
    var unitError by mutableStateOf(false)
    var typeError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)

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
        val workout = WorkoutsRepository.getWorkouts(code).find { it.date == date } ?: return
        uiState.description = workout.description ?: ""
        uiState.day = "${LocalDate.parse(workout.date).dayOfMonth}"
        uiState.month = "${LocalDate.parse(workout.date).monthValue}"
        uiState.year = "${LocalDate.parse(workout.date).year}"
        uiState.restDay = workout.restDay ?: false
        uiState.amount = "${workout.amount ?: 0}"
        uiState.unit = workout.unit ?: ""
        uiState.type = workout.type ?: ""
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

    fun updateAmount(input: String) {
        uiState.amountError = false
        uiState.errorMessage = ""
        try {
            input.filter { !it.isWhitespace() }.toInt()
        } catch (e: Exception) {
            uiState.amountError = true
            uiState.errorMessage = "Amount must be a number"
        } finally {
            uiState.amount = input.filter { !it.isWhitespace() }
        }
    }

    suspend fun saveWorkout() {
        if (uiState.dayError || uiState.monthError || uiState.yearError || uiState.amountError) return

        uiState.errorMessage = ""
        uiState.unitError = false
        uiState.typeError = false

        if (uiState.unit.isEmpty()) {
            uiState.unitError = true
            uiState.errorMessage = "Unit cannot be blank"
            return
        }
        if (uiState.type.isEmpty()) {
            uiState.typeError = true
            uiState.errorMessage = "Type cannot be blank"
            return
        }

        val workout = WorkoutsRepository.getWorkouts(code!!).find { it.date == date } ?: return
        WorkoutsRepository.updateWorkout(
            workout.copy(
                date = "${uiState.year}-${uiState.month}-${uiState.day}",
                amount = uiState.amount.toFloat(),
                type = uiState.unit,
                unit = uiState.type,
                description = uiState.description,
                restDay = uiState.restDay
            )
        )

        uiState.saveSuccess = true
    }
}