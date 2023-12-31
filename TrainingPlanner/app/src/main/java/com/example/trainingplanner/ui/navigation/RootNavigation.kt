package com.example.trainingplanner.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trainingplanner.ui.repositories.UserRepository
import com.example.trainingplanner.ui.screens.DashboardScreen
import com.example.trainingplanner.ui.screens.JoinPlanScreen
import com.example.trainingplanner.ui.screens.LaunchScreen
import com.example.trainingplanner.ui.screens.PurchaseScreen
import com.example.trainingplanner.ui.screens.SignInScreen
import com.example.trainingplanner.ui.screens.SignUpScreen
import com.example.trainingplanner.ui.screens.SplashScreen
import com.example.trainingplanner.ui.screens.TrainingPlanEditorScreen
import com.example.trainingplanner.ui.screens.WorkoutEditorScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        UserRepository.logout()
                        navController.navigate(Routes.launchNavigation.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                    CenterAlignedTopAppBar(
                        title = { Text("Training Planner") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if(isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu button")
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Routes.splashScreen.route,
                modifier = Modifier.padding(paddingValues = it)
            ) {
                navigation(route = Routes.launchNavigation.route, startDestination = Routes.launch.route) {
                    composable(route = Routes.launch.route) { LaunchScreen(navController) }
                    composable(route = Routes.signIn.route) { SignInScreen(navController) }
                    composable(route = Routes.signUp.route) { SignUpScreen(navController) }
                    composable(route = Routes.joinPlanScreen.route) { JoinPlanScreen(navController) }
                    composable(route = Routes.purchase.route) { PurchaseScreen(navController) }
                }
                navigation(route = Routes.appNavigation.route, startDestination = Routes.dashboard.route) {
                    composable(route = Routes.dashboard.route) { DashboardScreen(navController) }
                    composable(route = Routes.trainingPlanEditor.route) { TrainingPlanEditorScreen(navController) }
                    composable(
                        route = "${Routes.workoutEditor.route}?date={date}&code={code}",
                        arguments = listOf(
                            navArgument("date") { defaultValue = "new" },
                            navArgument("code") { defaultValue = "new" }
                        )
                    ) { navBackStackEntry ->
                        WorkoutEditorScreen(navController, navBackStackEntry.arguments?.getString("date"), navBackStackEntry.arguments?.getString("code"))
                    }
                }
                composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
            }
        }
    }
}