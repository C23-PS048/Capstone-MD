package com.bangkit.capstone_project.helper

import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor

fun getAddressName(context:Context,lat: Double, lon: Double): MutableState<String?> {
    var addressName: MutableState<String?> = mutableStateOf(null)
    val geocoder = Geocoder(context, Locale.US)
    try {
        val list = geocoder.getFromLocation(lat, lon, 1)
        if (list != null && list.size != 0) {
            addressName = mutableStateOf("${list[0].subAdminArea}, ${list[0].countryName}")

        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return addressName
}

fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e("kilo", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}