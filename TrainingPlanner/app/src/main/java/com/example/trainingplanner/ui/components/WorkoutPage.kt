package com.example.trainingplanner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import com.example.trainingplanner.ui.models.Workout
import com.example.trainingplanner.ui.theme.TrainingPlannerTheme
import java.time.LocalDate

@Composable
public fun WorkoutPage(
    workout: Workout,
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
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = workout.getDate(), fontWeight = FontWeight.Bold)

            Text(
                text = workout.title ?: "No title",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(text = workout.description ?: "No description")
            Button(onClick = toggle) {
                if (!workout.userCompleted!!) {
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

@Preview
@Composable
fun WorkoutPagePreview() {
    TrainingPlannerTheme {
        WorkoutPage(workout = Workout(
            id = "123",
            userId = "abc",
            title = "4 mile run",
            description = "go on a 4 mile run",
            day = 1,
            month = 2,
            year = 2023,
            userCompleted = false
        ))
    }
}