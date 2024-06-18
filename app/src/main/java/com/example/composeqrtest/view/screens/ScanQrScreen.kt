package com.example.composeqrtest.view.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeqrtest.R
import com.example.composeqrtest.databinding.LayoutCustomBarcodeBinding
import com.example.composeqrtest.ui.theme.accent
import com.example.composeqrtest.ui.theme.primary
import com.example.composeqrtest.ui.theme.white
import com.example.composeqrtest.utilities.findActivity
import com.example.composeqrtest.view.navhost.NavigationScreens
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.client.android.BeepManager
import com.google.zxing.common.HybridBinarizer
import com.journeyapps.barcodescanner.camera.CameraSettings
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanQrScreen(navController: NavHostController) {
    val context = LocalContext.current
    var cleanupQrRes: (() -> Unit)? = null
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if(uri != null) {
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val width = bitmap.width
                    val height = bitmap.height
                    val pixels = IntArray(width*height)
                    bitmap.getPixels(pixels, 0, width,0, 0, width, height)
                    bitmap.recycle()

                    val source = RGBLuminanceSource(width, height, pixels)
                    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader = MultiFormatReader()

                    val result = reader.decode(binaryBitmap)
                    navController.navigate("${NavigationScreens.ResultQr.route}/${result.text}")
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    )

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
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .clip(shape = CircleShape),
                title = {
                    Text(
                        fontSize = 16.sp,
                        text = "Scan your QR CODE",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(primary)
                                .padding(4.dp),
                            imageVector = Icons.Default.ArrowBack,
                            tint = white,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = accent,
                    titleContentColor = primary,
                )
            )
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp),
                painter = painterResource(id = R.drawable.scan_me2),
                contentDescription = stringResource(id = R.string.app_name)
            )
            AndroidView(
                factory = {
                    View.inflate(context, R.layout.layout_custom_barcode, null)
                },
                update = {
                    cleanupQrRes = loadCameraAndBeepManager(navController, context, it)
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    launcher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(10.dp))
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
                    text = "Add QR code from device"
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cleanupQrRes?.invoke()
        }
    }
}

fun loadCameraAndBeepManager(
    navController: NavHostController, context: Context, view: View
) : () -> Unit {
    val beepManager = BeepManager(context.findActivity())
    beepManager.isBeepEnabled = true
    beepManager.isVibrateEnabled = true
    val binding = LayoutCustomBarcodeBinding.bind(view)
    binding.barcodeView.apply {
        resume()
        val settings = CameraSettings()
        settings.requestedCameraId = 0
        cameraSettings  = settings
        decodeSingle { result ->
            beepManager.playBeepSound()
            val resultText = result?.result?.text ?: ""
            val urlData = URLEncoder.encode(
                resultText, StandardCharsets.UTF_8.toString()
            )
            try {
                pause()
                navController.navigate(
                    "${NavigationScreens.ResultQr.route}/$urlData"
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                Toast.makeText(
                    context, "Not match code", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    return {
        binding.barcodeView.pause()
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewScanQrScreen() {
    ScanQrScreen( navController = rememberNavController() )
}