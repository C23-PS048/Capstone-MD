package com.bangkit.capstone_project.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
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
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor

fun getAddressName(context: Context, lat: Double, lon: Double): MutableState<String?> {
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

fun rotateImage(bitmap: Bitmap): Bitmap {
    val matrix = Matrix()
    val rotate = 90f
    matrix.postRotate(rotate)



    val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    return result
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

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
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


@SuppressLint("SimpleDateFormat")
fun convertMillisToDateString(milliseconds: Long): String {
    val date = Date(milliseconds)
    val format = SimpleDateFormat("yyyy-MM-dd") // Customize the date format as per your requirement
    return format.format(date)
}

fun calculateScheduleDates(startDate: Long, frequency: Int): Pair<Long, Long> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = startDate


    val lastScheduledDate = calendar.timeInMillis


    when (frequency) {
        1 -> calendar.add(Calendar.MONTH, 1)
        2 -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
        3 -> calendar.add(Calendar.DAY_OF_YEAR, 3)
        4 -> calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    val nextScheduledDate = calendar.timeInMillis

    return Pair(lastScheduledDate, nextScheduledDate)
}