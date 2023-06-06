package com.bangkit.capstone_project.ui.screen

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bangkit.capstone_project.getCameraProvider
import com.bangkit.capstone_project.helper.takePhoto
import com.bangkit.capstone_project.ui.component.buttons.CameraButton
import com.bangkit.capstone_project.ui.theme.GreenDark
import com.bangkit.capstone_project.ui.theme.Ivory
import java.io.File
import java.util.concurrent.Executor

@Composable
fun DiagnoseScreen(
    modifier: Modifier = Modifier,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = remember { Preview.Builder().build() }
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    LaunchedEffect(key1 = Unit) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }
    Scaffold(
        topBar = {
          Box( modifier = Modifier.fillMaxWidth()) {
              IconButton(onClick = onBack, Modifier.align(Alignment.CenterStart)) {
                  Icon(
                      imageVector = Icons.Default.ArrowBack,
                      contentDescription = "Back Button",
                      tint = GreenDark
                  )
              }
              Text(text = "Diagnose Your Plant", modifier = Modifier.align(Alignment.Center))
          }
        },
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                CameraButton(
                    modifier = Modifier

                        .padding(bottom = 20.dp),
                    onclick = {
                        takePhoto(
                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            imageCapture = imageCapture,
                            outputDirectory = outputDirectory,
                            executor = executor,
                            onImageCaptured = onImageCaptured,
                            onError = onError,
                            pickedImageUri = null
                        )
                    },

                    )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(Ivory)
                .padding(32.dp)
                .padding(it)
                .clip(RoundedCornerShape(10))
        ) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        }
    }
    Box(modifier = modifier) {


    }
}