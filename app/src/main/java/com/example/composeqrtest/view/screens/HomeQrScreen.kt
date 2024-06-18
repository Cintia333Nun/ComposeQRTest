package com.example.composeqrtest.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeqrtest.R
import com.example.composeqrtest.ui.theme.primary
import com.example.composeqrtest.view.navhost.NavigationScreens

@Composable
fun HomeQrScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(
                BorderStroke(2.dp, color = primary),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Column(
            Modifier
                .padding(top = 80.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                painter = painterResource(id = R.drawable.scan_me),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    navController.navigate(NavigationScreens.ScanQr.route)
                }
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF0F98D5),
                                    Color(0xFF97C93F)
                                )
                            )
                        )
                        .padding(all = 14.dp),
                    text = "Scan QR"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    navController.navigate(NavigationScreens.HomeQr.route) {
                        popUpTo(NavigationScreens.ScanQr.route) {
                            inclusive = true
                        }
                    }
                    //navController.navigate(NavigationScreens.HomeQr.route)
                }
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF0F98D5),
                                    Color(0xFF97C93F)
                                )
                            )
                        )
                        .padding(all = 14.dp),
                    text = "Generate QR Code"
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewHomeQrScreen() {
    HomeQrScreen(navController = rememberNavController())
}