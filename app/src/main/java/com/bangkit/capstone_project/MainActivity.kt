package com.bangkit.capstone_project

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone_project.data.local.LoginPreference
import com.bangkit.capstone_project.data.local.PreferenceFactory

import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.App
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel
import com.bangkit.capstone_project.viewmodel.task.TaskVMFactory
import com.bangkit.capstone_project.viewmodel.task.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val mInputSize = 224
    private val mModelPath = "chili_disease.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: DeseaseClassifier

    private var currentState: MutableState<ScreenState> = mutableStateOf(ScreenState.Camera)
    private lateinit var taskViewModel: TaskViewModel

    private lateinit var prefViewModel: PreferenceViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        val allGranted = permission.entries.all { it.value }
        if (allGranted) {

            currentState.value = ScreenState.Camera
        }
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
        if (permission[notificationPermission] == true) {
            // Notification permission granted
            // Perform necessary actions here
        } else {
            // Notification permission denied
            // Handle the denial or show a message to the user
        }
    }

    private fun initClassifier() {
        classifier = DeseaseClassifier(assets, mModelPath, mLabelPath, mInputSize)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissionsOnFirstLaunch() {
        val cameraPermission = Manifest.permission.CAMERA
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
        val permissionsToRequest = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                notificationPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(notificationPermission)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                cameraPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(cameraPermission)
        }

        for (locationPermission in locationPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    locationPermission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(locationPermission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            currentState.value = ScreenState.Camera
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val pref = LoginPreference.getInstance(dataStore)
        prefViewModel =
            ViewModelProvider(this, PreferenceFactory(pref))[PreferenceViewModel::class.java]
        setContent {
            taskViewModel = obtainViewModel(this@MainActivity)
            initClassifier()
            requestPermissionsOnFirstLaunch()
            outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()



            CapstoneProjectTheme {
                LaunchedEffect(Unit) {
                    taskViewModel.uiState.collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {

                            }
                            is UiState.Success ->{}
                            is UiState.Error ->{}
                            // Handle other UI states if needed
                        }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(
                        context = this,
                        currentState = currentState,
                        outputDirectory = outputDirectory,
                        cameraExecutor = cameraExecutor,
                        classifier = classifier,
                        taskViewModel = taskViewModel,
                        prefViewModel = prefViewModel,
                        showToast = {text->showToast(text)},
                        sendNotification={}
                    )

                }
            }


        }
    }

    private fun obtainViewModel(activity: ComponentActivity): TaskViewModel {
        val factory = TaskVMFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TaskViewModel::class.java]
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
    @SuppressLint("ServiceCast")
    fun sendNotification() {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.bell)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bell))
            .setContentTitle("Test")
            .setContentText("Content")
            .setSubText("Subtext")
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }
}

sealed class ScreenState {
    object Camera : ScreenState()
    object Photo : ScreenState()
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }




}