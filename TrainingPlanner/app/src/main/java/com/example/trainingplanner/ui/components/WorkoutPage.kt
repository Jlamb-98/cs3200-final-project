package com.example.trainingplanner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
public fun WorkoutPage(
    workout: Workout,
    username: String,
    offsetX: Float = 0f,
    toggle: () -> Unit = {},
    onEditPressed: () -> Unit = {}
) {
    // TODO: Make this look nicer
    Surface(
        color = Color.LightGray,
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
                Button(onClick = { /*TODO edit workout screen*/ }) {
                    Text("Create workout")
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
                IconButton(onClick = onEditPressed, content = {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                })
                // TODO: show list of members that have completed workout
            }
        }
    }
}

@Preview
@Composable
fun CreatedWorkoutPagePreview() {
    TrainingPlannerTheme {
        WorkoutPage(workout = Workout(
            title = "4 mile run",
            description = "go on a 4 mile run",
            date = "2000-01-02",
            membersCompleted = mutableListOf("BillyRuns", "HarryFastCheeks"),
        ),
            username = "Fred123")
    }
}

//@Preview
//@Composable
//fun NoWorkoutPagePreview() {
//    TrainingPlannerTheme {
//        WorkoutPage(workout = Workout())
//    }
//}