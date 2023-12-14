package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.repositories.UserRepository
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
    LaunchedEffect(true) {
//        UserRepository.logout() // TODO: REMOVE; uncomment if you get stuck while the user is logged in
        delay(1000)
        navHostController.navigate(
            if (UserRepository.getCurrentUserId() == null) {
                println("Splash Screen to Launch Navigation")
                Routes.launchNavigation.route
            } else if (UserRepository.getUser().trainingPlanCode.isEmpty()) {
                println("Splash Screen to New Plan Screen")
                Routes.joinPlanScreen.route
            } else {
                println("Splash Screen to App Navigation")
                Routes.appNavigation.route
            }
        ) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Training Planner",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "USU CS3200",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}