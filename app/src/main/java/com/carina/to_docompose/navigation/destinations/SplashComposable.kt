package com.carina.to_docompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.carina.to_docompose.ui.screens.splash.SplashScreen
import com.carina.to_docompose.util.Constants
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@ExperimentalMaterialApi
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SPLASH_SCREEN
    ) {
        SplashScreen(
            navigateToListScreen = navigateToListScreen
        )

    }
}