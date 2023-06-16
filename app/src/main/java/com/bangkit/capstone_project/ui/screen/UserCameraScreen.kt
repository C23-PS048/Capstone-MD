package com.bangkit.capstone_project.ui.screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.getCameraProvider
import com.bangkit.capstone_project.helper.takePhoto
import com.bangkit.capstone_project.ui.component.buttons.CameraButton
import com.bangkit.capstone_project.ui.theme.GreenDark
import com.bangkit.capstone_project.ui.theme.Ivory
import java.io.File
import java.util.concurrent.Executor

@Composable
fun UserCameraScreen(
    modifier: Modifier = Modifier,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onBack: () -> Unit,
    isBack: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = remember { Preview.Builder().build() }
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val isFrontCamera = remember { mutableStateOf(false) }


    val pickedImageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri = data?.data
            if (uri != null) {
                pickedImageUri.value = uri

                takePhoto(
                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                    imageCapture = imageCapture,
                    outputDirectory = outputDirectory,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError,
                    pickedImageUri = uri
                )
            }
        }
    }

    LaunchedEffect(isFrontCamera.value) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()

        val updatedCameraSelector = CameraSelector.Builder().apply {
            if (isFrontCamera.value) {
                requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            } else {
                requireLensFacing(CameraSelector.LENS_FACING_BACK)
            }
        }.build()

        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            updatedCameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    Scaffold(
        topBar ={
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack, Modifier.align(Alignment.CenterStart)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button",
                        tint = GreenDark
                    )
                }
                Text(text = "Ambil Foto Anda", modifier = Modifier.align(Alignment.Center))
            }
        },
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 16.dp)) {
                CameraButton(
                    onclick = {
                        takePhoto(
                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            imageCapture = imageCapture,
                            outputDirectory = outputDirectory,
                            executor = executor,
                            onImageCaptured = onImageCaptured,
                            onError = onError,
                            pickedImageUri = null,
                            isBack = !isFrontCamera.value
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(48.dp)
                )

                IconButton(
                    onClick = {
                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        launcher.launch(galleryIntent)
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gallery),
                        contentDescription = "Pick Image From Gallery Button"
                    )
                }

                IconButton(
                    onClick = {
                        isFrontCamera.value = !isFrontCamera.value
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.scanner),
                        contentDescription = "Flip Camera Button"
                    )
                }
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
}
