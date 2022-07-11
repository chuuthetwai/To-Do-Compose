package com.carina.to_docompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.carina.to_docompose.ui.screens.list.ListScreen
import com.carina.to_docompose.ui.viewmodel.SharedViewModel
import com.carina.to_docompose.util.Action
import com.carina.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import com.carina.to_docompose.util.Constants.LIST_SCREEN
import com.carina.to_docompose.util.toAction
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        var myAction by rememberSaveable{ mutableStateOf(Action.NO_ACTION) }

        LaunchedEffect(key1 = myAction){
            if(action != myAction){
                myAction = action
                sharedViewModel.updateAction(action)
            }
        }

        val databaseAction = sharedViewModel.action
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel,
            databaseAction
        )

    }
}