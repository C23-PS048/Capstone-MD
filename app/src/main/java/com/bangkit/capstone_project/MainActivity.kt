package com.bangkit.capstone_project

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone_project.data.local.LoginPreference
import com.bangkit.capstone_project.data.local.PreferenceFactory
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.App
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
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val mInputSize = 224
    private val mModelPath = "chili_disease.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: DeseaseClassifier

    private var currentState: MutableState<ScreenState> = mutableStateOf(ScreenState.Camera)
    private lateinit var taskViewModel: TaskViewModel

    private lateinit var prefViewModel: PreferenceViewModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        val allGranted = permission.entries.all { it.value }
        if (allGranted) {

            currentState.value = ScreenState.Camera
        }
    }

    private fun initClassifier() {
        classifier = DeseaseClassifier(assets, mModelPath, mLabelPath, mInputSize)
    }

    private fun requestPermissionsOnFirstLaunch() {
        val cameraPermission = Manifest.permission.CAMERA
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val permissionsToRequest = mutableListOf<String>()

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
                        prefViewModel = prefViewModel
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