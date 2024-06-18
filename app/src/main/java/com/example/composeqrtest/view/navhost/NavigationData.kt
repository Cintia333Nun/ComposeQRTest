package com.example.composeqrtest.view.navhost

enum class ScreenType {
    HOME_QR, SCAN_QR, GENERATE_QR,
    RESULT_QR, IMAGE_PICKER_QR
}

sealed class NavigationScreens(val route: String) {
    data object HomeQr : NavigationScreens(ScreenType.HOME_QR.name)
    data object ScanQr : NavigationScreens(ScreenType.SCAN_QR.name)
    data object ResultQr : NavigationScreens(ScreenType.RESULT_QR.name)
}