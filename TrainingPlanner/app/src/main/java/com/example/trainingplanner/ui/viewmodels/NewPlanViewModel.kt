package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trainingplanner.ui.models.User
import com.example.trainingplanner.ui.repositories.UserRepository

class NewPlanScreenState {
    var user: User? = null
    var planCode = ""
    var planCodeError = false
    var errorMessage = ""


}

class NewPlanViewModel(application: Application): AndroidViewModel(application) {
    val uiState = NewPlanScreenState()

    suspend fun getUser() {
        uiState.user = UserRepository.getUser()
    }

    fun joinTrainingPlan() {
        // TODO: search for training plan using planCode
    }
}