package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.components.FormField
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.viewmodels.NewPlanViewModel

@Composable
fun NewPlanScreen(navHostController: NavHostController) {
    val viewModel: NewPlanViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(true) {
//        viewModel.getUser()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Join existing training plan:")
                Row(
                    modifier = Modifier.width(128.dp)
                ) {
                    FormField(
                        value = state.planCode,
                        onValueChange = { state.planCode = it },
                        placeholder = { Text("Code") },
                        error = state.planCodeError
                    )
                }
                Button(onClick = { viewModel.joinTrainingPlan() }) {
                    Text("Search")
                }
                Text("or Create a new training plan")
                Button(onClick = { navHostController.navigate(Routes.trainingPlanEditor.route) }) {
                    Text("Start")
                }
            }
        }
    }
}