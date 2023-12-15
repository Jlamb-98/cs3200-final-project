package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.viewmodels.NewPlanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinPlanScreen(navHostController: NavHostController) {
    val viewModel: NewPlanViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    LaunchedEffect(true) {
        viewModel.getUser()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { navHostController.navigate(Routes.purchase.route) }) {
                    Text("Create new training plan")
                }

                Text("or")

                Row(
                    modifier = Modifier.width(128.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        value = state.planCode,
                        onValueChange = { state.planCode = it },
                        label = { Text("Code") },
                        isError = state.planCodeError
                    )
                }
                Button(onClick = {
                    scope.launch {
                        viewModel.joinTrainingPlan()
                        if (state.joinSuccess) {
                            navHostController.navigate(Routes.appNavigation.route) {
                                popUpTo(navHostController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }) {
                    Text("Join existing training plan")
                }
                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}