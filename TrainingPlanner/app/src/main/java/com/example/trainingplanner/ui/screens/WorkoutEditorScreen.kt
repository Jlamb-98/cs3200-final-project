package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.trainingplanner.ui.viewmodels.WorkoutEditorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEditorScreen(navHostController: NavHostController, date: String?, code: String?) {
    val viewModel: WorkoutEditorViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    
    LaunchedEffect(true) {
        viewModel.setupInitialState(date, code)
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
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.day,
                        onValueChange = { viewModel.updateDay(it) },
                        placeholder = { Text("") },
                        label = { Text("Day") },
                        enabled = false,
                        readOnly = true,
                        isError = state.dayError
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.month,
                        onValueChange = { viewModel.updateMonth(it) },
                        placeholder = { Text("") },
                        label = { Text("Month") },
                        enabled = false,
                        readOnly = true,
                        isError = state.monthError
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.year,
                        onValueChange = { viewModel.updateYear(it) },
                        placeholder = { Text("") },
                        label = { Text("Year") },
                        enabled = false,
                        readOnly = true,
                        isError = state.yearError
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.type,
                        onValueChange = { state.type = it },
                        placeholder = { Text("") },
                        label = { Text("Type") },
                        isError = state.typeError
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.unit,
                        onValueChange = { state.unit = it },
                        placeholder = { Text("") },
                        label = { Text("Units") },
                        isError = state.unitError
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.amount,
                        onValueChange = { viewModel.updateAmount(it) },
                        placeholder = { Text("") },
                        label = { Text("Amount") },
                        isError = state.amountError
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                FormField(
                    value = state.description,
                    onValueChange = { state.description = it },
                    placeholder = { Text("Description") },
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