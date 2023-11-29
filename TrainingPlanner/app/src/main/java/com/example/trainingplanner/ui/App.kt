package com.example.trainingplanner.ui

import androidx.compose.runtime.Composable
import com.example.trainingplanner.ui.navigation.RootNavigation
import com.example.trainingplanner.ui.theme.TrainingPlannerTheme

@Composable
fun App() {
    TrainingPlannerTheme {
        RootNavigation()
    }
}