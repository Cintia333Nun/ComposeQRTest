package com.example.composeqrtest.view.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeqrtest.view.screens.HomeQrScreen

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
    }
}

@Preview
@Composable
private fun PreviewQrNavHost() {
    QrNavHost(navController = rememberNavController())
}