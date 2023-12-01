package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.components.WorkoutPage
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.viewmodels.WorkoutsViewModel

@Composable
fun DashboardScreen(navHostController: NavHostController) {
    val viewModel: WorkoutsViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true) {
        viewModel.getWorkouts()
    }

    Column {
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
        ) {
            items(state.workouts, key = { it.id!! }) {workout ->
                WorkoutPage(
                    workout = workout,
                    onEditPressed = { navHostController.navigate("${Routes.workoutEditor.route}?id=${workout.id}")}
                )
            }
        }
    }
}