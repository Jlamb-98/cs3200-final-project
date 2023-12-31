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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.components.WorkoutPage
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.viewmodels.DashboardViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun DashboardScreen(navHostController: NavHostController) {
    val viewModel: DashboardViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.getTrainingPlan()
    }
    LaunchedEffect(state.translation.value >= state.OFFSET || state.translation.value <= -state.OFFSET) {
        viewModel.updateCurrentWorkout()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Group code: ${state.code}", style = MaterialTheme.typography.headlineSmall)
        Text(text = "${state.daysUntilEvent} days left!", style = MaterialTheme.typography.headlineSmall)
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
            Box {
                if (state.workoutsReady) {
                    if (state.selectedDate > LocalDate.parse(state.trainingPlan.startDate)) {
                        WorkoutPage(
                            workout = state.workouts[state.currentWorkout-1],
                            username = state.username,
                            userIsOrganizer = state.userIsOrganizer,
                            offsetX = state.translation.value-state.OFFSET
                        )
                    }
                    WorkoutPage(
                        workout = state.workouts[state.currentWorkout],
                        username = state.username,
                        userIsOrganizer = state.userIsOrganizer,
                        offsetX = state.translation.value,
                        toggle = {
                            scope.launch {
                                viewModel.toggleCompletion(state.workouts[state.currentWorkout])
                            }
                        },
                        onEditPressed = { navHostController.navigate("${Routes.workoutEditor.route}?date=${state.selectedDate}&code=${state.code}") }
                    )
                    if (state.selectedDate < LocalDate.parse(state.trainingPlan.eventDate)) {
                        WorkoutPage(
                            workout = state.workouts[state.currentWorkout+1],
                            username = state.username,
                            userIsOrganizer = state.userIsOrganizer,
                            offsetX = state.translation.value+state.OFFSET
                        )
                    }
                }
            }
        }
    }
}