package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.trainingplanner.ui.viewmodels.WorkoutEditorViewModel
import kotlinx.coroutines.launch

@Composable
fun WorkoutEditorScreen(navHostController: NavHostController, id: String?) {
    val viewModel: WorkoutEditorViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    
    LaunchedEffect(true) {
        viewModel.setupInitialState(id)
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
//                Text(text = state.title, style = MaterialTheme.typography.headlineSmall)    
                FormField(
                    value = state.title,
                    onValueChange = { state.title = it },
                    placeholder = { Text("Title") },
                    error = state.titleError
                )
                FormField(
                    value = state.description,
                    onValueChange = { state.description = it },
                    placeholder = { Text("Description") },
                    error = state.descriptionError
                )
                FormField(
                    value = state.day,
                    onValueChange = { state.day = it },
                    placeholder = { Text("Day") },
                    error = state.dayError
                )
                FormField(
                    value = state.month,
                    onValueChange = { state.month = it },
                    placeholder = { Text("Month") },
                    error = state.monthError
                )
                FormField(
                    value = state.year,
                    onValueChange = { state.year = it },
                    placeholder = { Text("Year") },
                    error = state.yearError
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
                                viewModel.saveWorkout()
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