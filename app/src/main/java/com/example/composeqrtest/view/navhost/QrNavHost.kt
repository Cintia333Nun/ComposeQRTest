package com.example.composeqrtest.view.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeqrtest.view.screens.HomeQrScreen
import com.example.composeqrtest.view.screens.ResultQrScreen
import com.example.composeqrtest.view.screens.ScanQrScreen

@Composable
fun QrNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationScreens.HomeQr.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationScreens.HomeQr.route) {
            HomeQrScreen(navController = navController)
        }
        composable(NavigationScreens.ScanQr.route) {
            ScanQrScreen(navController = navController)
        }
        composable(
            "${NavigationScreens.ResultQr.route}/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        ) {
            ResultQrScreen(
                navController = navController,
                it.arguments?.getString("data").toString()
            )
        }
    }
}