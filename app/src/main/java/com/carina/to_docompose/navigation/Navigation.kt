package com.carina.to_docompose.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.carina.to_docompose.navigation.destinations.listComposable
import com.carina.to_docompose.navigation.destinations.taskComposable
import com.carina.to_docompose.ui.viewmodel.SharedViewModel
import com.carina.to_docompose.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    val sharedViewModel = hiltViewModel<SharedViewModel>()
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel,
            navController
        )
        taskComposable(
            navigateToListScreen = screen.list,
            sharedViewModel = sharedViewModel,
            navController = navController
        )
    }
}