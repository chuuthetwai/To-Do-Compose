package com.carina.to_docompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.carina.to_docompose.R
import com.carina.to_docompose.ui.theme.fabBackgroundColor
import com.carina.to_docompose.ui.viewmodel.SharedViewModel
import com.carina.to_docompose.util.Action
import com.carina.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    action:Action
) {

    LaunchedEffect(key1 = action){
        sharedViewModel.handleDatabaseActions(actions = action)
    }


    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    val searchAppBarState = sharedViewModel.searchAppBarState

    val searchTextState = sharedViewModel.searchTextState

    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTask by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTask by sharedViewModel.highPriorityTasks.collectAsState()

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { sharedViewModel.updateAction(it) },
        taskTitle = sharedViewModel.title,
        action = action,
        onUndoClicked = {
            sharedViewModel.updateAction(it)
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                sharedViewModel,
                searchAppBarState,
                searchTextState
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                navigateToTaskScreen = navigateToTaskScreen,
                lowPriorityTasks = lowPriorityTask,
                highPriorityTasks = highPriorityTask,
                sortState = sortState,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.updateAction(action)
                    sharedViewModel.updatedTaskFields(selectedTask = task)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    )
    {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action, taskTitle),
                    actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }

}

private fun setActionLabel(action: Action): String {
    return if (action == Action.DELETE) {
        "UNDO"
    } else {
        "OK"
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed"
        else -> "${action.name}: $taskTitle"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}
