package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.components.FormField
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.viewmodels.TrainingPlanEditorViewModel
import com.example.trainingplanner.ui.viewmodels.WorkoutEditorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingPlanEditorScreen(navHostController: NavHostController) {
    val viewModel: TrainingPlanEditorViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(true) {
//        viewModel.setupInitialState(id)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Surface(shadowElevation = 2.dp) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = state.heading, style = MaterialTheme.typography.headlineSmall)

                FormField(
                    value = state.eventName,
                    onValueChange = { state.eventName = it },
                    placeholder = { Text("Event Name") },
                    error = state.eventNameError
                )
                FormField(
                    value = state.description,
                    onValueChange = { state.description = it },
                    placeholder = { Text("Description") },
                    error = state.descriptionError
                )
                FormField(
                    value = state.startDate,
                    onValueChange = { state.startDate = it },
                    placeholder = { Text("Start Date") },
                    error = state.startDateError
                )
                FormField(
                    value = state.eventDate,
                    onValueChange = { state.eventDate = it },
                    placeholder = { Text("Event Date") },
                    error = state.eventDateError
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.saveTrainingPlan()
                                if (state.saveSuccess) {
                                    navHostController.navigate(Routes.appNavigation.route) {
                                        popUpTo(navHostController.graph.id) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        },
                        elevation = null
                    ) {
                        Text("Save")
                    }
                }
                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}