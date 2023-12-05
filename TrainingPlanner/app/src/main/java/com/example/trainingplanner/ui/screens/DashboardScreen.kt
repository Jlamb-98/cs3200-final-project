package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.components.WorkoutPage
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.viewmodels.DashboardViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun DashboardScreen(navHostController: NavHostController) {
    val viewModel: DashboardViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true) {
        viewModel.getWorkouts()
    }
    LaunchedEffect(state.translation.value >= state.OFFSET || state.translation.value <= -state.OFFSET) {
        viewModel.updateCurrentWorkout()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .pointerInput(Unit) {
                    coroutineScope {
                        while (true) {
                            // stop animation when screen is touched
                            val pointerId = awaitPointerEventScope {
                                awaitFirstDown().id
                            }
                            state.translation.stop()

                            awaitPointerEventScope {
                                horizontalDrag(pointerId) {
                                    launch {
                                        viewModel.dragPage(it.positionChange().x)
                                    }
                                }
                            }

                            // when page is released, animate to center
                            launch {
                                viewModel.animateToCenter()
                            }
                        }
                    }
                }
        ) {
            Box() {
                WorkoutPage(
                    workout = Workout(
                        id="123",
                        userId="asdf",
                        title="Run 1 mile",
                        description = "As fast as you can",
                        date = LocalDate.parse("2023-12-11"),
                        userCompleted = false
                    ),
                    offsetX = state.translation.value-state.OFFSET
                )
                WorkoutPage(
                    workout = Workout(
                        id="123",
                        userId="asdf",
                        title="Run 1 mile",
                        description = "As fast as you can",
                        date = LocalDate.parse("2023-12-12"),
                        userCompleted = false
                    ),
                    offsetX = state.translation.value
                )
                WorkoutPage(
                    workout = Workout(
                        id="123",
                        userId="asdf",
                        title="Run 1 mile",
                        description = "As fast as you can",
                        date = LocalDate.parse("2023-12-13"),
                        userCompleted = false
                    ),
                    offsetX = state.translation.value+state.OFFSET
                )
            }
        }
    }
}