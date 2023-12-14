package com.example.trainingplanner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.theme.TrainingPlannerTheme

@Composable
fun WorkoutPage(
    workout: Workout,
    username: String,
    userIsOrganizer: Boolean = false,
    offsetX: Float = 0f,
    toggle: () -> Unit = {},
    onEditPressed: () -> Unit = {}
) {
    // TODO: Make this look nicer
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .graphicsLayer { translationX = offsetX }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = workout.date!!, fontWeight = FontWeight.Bold)
            if (workout.title == null) {
                Text("No workout scheduled")
                if (userIsOrganizer) {
                    Button(onClick = onEditPressed) {
                        Text("Create workout")
                    }
                }
            } else {
                Text(
                    text = workout.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(text = workout.description ?: "No description")
                Button(onClick = toggle) {
                    if (!workout.membersCompleted.contains(username)) {
                        Text(text = "Mark as Complete")
                    } else {
                        Text(text = "Undo Completion")
                    }
                }
                if (userIsOrganizer) {
                    IconButton(onClick = onEditPressed, content = {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                    })
                }

                LazyColumn() {
                    items(workout.membersCompleted) {member ->
                        MemberItem(username = member!!)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CreatedWorkoutPagePreview() {
    TrainingPlannerTheme {
        WorkoutPage(
            workout = Workout(
                title = "4 mile run",
                description = "go on a 4 mile run",
                date = "2000-01-02",
                membersCompleted = mutableListOf("BillyRuns", "HarryFastCheeks"),
            ),
            username = "Fred123",
            userIsOrganizer = true
        )
    }
}

//@Preview
//@Composable
//fun NoWorkoutPagePreview() {
//    TrainingPlannerTheme {
//        WorkoutPage(workout = Workout())
//    }
//}