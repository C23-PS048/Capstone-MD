package com.bangkit.capstone_project.helper

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Geocoder
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

fun getAddressName(context: Context, lat: Double, lon: Double): String{
    var addressName:String = "semarang"
    val geocoder = Geocoder(context, Locale.US)

        val list = geocoder.getFromLocation(lat, lon, 1)
        if (list != null && list.size != 0) {
            addressName = "${list[0].subAdminArea}, ${list[0].countryName}"

        }

    return addressName
}

fun rotateImage(bitmap: Bitmap, isBack: Boolean = false): Bitmap {
    val matrix = Matrix()

    val rotate = if (isBack) 90f else -90f
    matrix.postRotate(rotate)
    if (!isBack) {
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
    }


    val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    return result
}

fun getFirstWord(str: String): String {
    val trimmedStr = str.trim()
    val firstSpaceIndex = trimmedStr.indexOf(' ')
    return if (firstSpaceIndex == -1) {
        trimmedStr
    } else {
        trimmedStr.substring(0, firstSpaceIndex)
    }
}
fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    pickedImageUri: Uri?,
    isBack: Boolean = false

) {
    if (pickedImageUri != null) {
        onImageCaptured(pickedImageUri)
    } else {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e("kilo", "Take photo error:", exception)
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    var savedUri = Uri.fromFile(photoFile)

                    // Rotate the image if taken from the camera
                    val isTakenFromCamera = pickedImageUri == null
                    if (isTakenFromCamera) {
                        val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                        val rotatedBitmap = rotateImage(bitmap,isBack)
                        val rotatedFile = File(outputDirectory, photoFile.name)
                        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(rotatedFile))
                        savedUri = Uri.fromFile(rotatedFile)
                    }

                    onImageCaptured(savedUri)
                }
            }
        )
    }
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
        1 -> calendar.add(Calendar.DAY_OF_YEAR, 1)
        2 -> calendar.add(Calendar.DAY_OF_YEAR, 2)
        3 -> calendar.add(Calendar.DAY_OF_YEAR, 3)
        4 -> calendar.add(Calendar.DAY_OF_YEAR, 4)
        5 -> calendar.add(Calendar.DAY_OF_YEAR, 5)
        6 -> calendar.add(Calendar.DAY_OF_YEAR, 6)
    }
    val nextScheduledDate = calendar.timeInMillis

    return Pair(lastScheduledDate, nextScheduledDate)
}

fun getTimeBetween(startMillis: Long, endMillis: Long): Long {
    val differenceMillis = endMillis - startMillis
    return TimeUnit.MILLISECONDS.toHours(differenceMillis)
}
fun convertHoursToDays(hours: Long): Long {
    return TimeUnit.HOURS.toDays(hours)
}

fun decodeUriAsBitmap(context: Context, uri: Uri?): Bitmap? {
    var bitmap: Bitmap? = null
    bitmap = try {
        BitmapFactory.decodeStream(
            context
                .contentResolver.openInputStream(uri!!)
        )
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }
    return bitmap
}


fun getCurrentDate(): String {
    val currentDate = Calendar.getInstance().time
    val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))

    return sdf.format(currentDate)
}

fun convertToHyphenated(input: String): String {
    val lowerCasedInput = input.lowercase()
    return lowerCasedInput.replace(" ", "-")
}




private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private const val MAXIMAL_SIZE = 1000000
val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT, Locale.US
).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)
    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } >= 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5


    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file

}