package com.carina.to_docompose.navigation

import androidx.navigation.NavHostController
import com.carina.to_docompose.util.Action
import com.carina.to_docompose.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (action: Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val task: (taskId:Int) -> Unit = {taskId ->
        navController.navigate("task/$taskId")
    }
}