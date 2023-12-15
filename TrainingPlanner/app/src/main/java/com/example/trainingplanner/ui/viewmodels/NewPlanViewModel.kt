package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.PurchasesUpdatedListener
import com.example.trainingplanner.ui.models.User
import com.example.trainingplanner.ui.repositories.TrainingPlanRepository
import com.example.trainingplanner.ui.repositories.UserRepository

class NewPlanScreenState {
    var user: User? = null
    var planCode by mutableStateOf("")
    var planCodeError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var joinSuccess by mutableStateOf(false)
}

class NewPlanViewModel(application: Application): AndroidViewModel(application) {
    val uiState = NewPlanScreenState()

    suspend fun getUser() {
        uiState.user = UserRepository.getUser()
    }

    fun updatePlanCode(code: String) {
        // TODO: if time, prevents more than 6 characters from being typed
    }

    suspend fun joinTrainingPlan() {
        uiState.errorMessage = ""
        uiState.planCodeError = false

        if (uiState.planCode.isEmpty()) {
            uiState.planCodeError = true
            uiState.errorMessage = "Please enter Training Plan code"
            return
        }

        if (!TrainingPlanRepository.addMember(uiState.planCode.uppercase(), uiState.user!!.id!!)) {
            uiState.planCodeError = true
            uiState.errorMessage = "Cannot find Training Plan"
            return
        }

        uiState.joinSuccess = true
    }


}