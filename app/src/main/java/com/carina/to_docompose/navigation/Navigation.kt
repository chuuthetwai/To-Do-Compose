package com.carina.to_docompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.carina.to_docompose.navigation.destinations.listComposable
import com.carina.to_docompose.navigation.destinations.splashComposable
import com.carina.to_docompose.navigation.destinations.taskComposable
import com.carina.to_docompose.ui.viewmodel.SharedViewModel
import com.carina.to_docompose.util.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    val sharedViewModel = hiltViewModel<SharedViewModel>()
    AnimatedNavHost(navController = navController, startDestination = SPLASH_SCREEN) {

        splashComposable (
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
    }
}