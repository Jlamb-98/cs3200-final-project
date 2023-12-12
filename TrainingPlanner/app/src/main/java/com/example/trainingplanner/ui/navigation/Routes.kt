package com.example.trainingplanner.ui.navigation

data class Screen(val route: String)

object Routes {
    val launchNavigation = Screen("launchnavigation")
    val appNavigation = Screen("appnavigation")

    val launch = Screen("launch")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val splashScreen = Screen("splashscreen")

    val newPlanScreen = Screen("newplan")
    val dashboard = Screen("dashboard")
    val invite = Screen("invite")
    val trainingPlanEditor = Screen("planeditor")
    val workoutEditor = Screen("workouteditor")
    val payment = Screen("payment")

    // extra-miler screens
    val calendarView = Screen("calendarview")
    val settings = Screen("settings")
    val announcements = Screen("announcements")
}