package com.carina.to_docompose.navigation

import androidx.navigation.NavHostController
import com.carina.to_docompose.util.Action
import com.carina.to_docompose.util.Constants.LIST_SCREEN
import com.carina.to_docompose.util.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION.name}"){
            popUpTo(SPLASH_SCREEN) {
                inclusive = true
            }
        }
    }
    val task: (action: Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val list: (taskId:Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}