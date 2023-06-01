package com.bangkit.capstone_project.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.capstone_project.ScreenState
import com.bangkit.capstone_project.data.LocationViewModel
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.component.navigation.NavigationBottomBar
import com.bangkit.capstone_project.ui.screen.CameraScreen
import com.bangkit.capstone_project.ui.screen.ForumScreen
import com.bangkit.capstone_project.ui.screen.HomeScreen
import com.bangkit.capstone_project.ui.screen.ResultScreen
import com.bangkit.capstone_project.ui.screen.Screen
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

    locationViewModel: LocationViewModel = hiltViewModel(),
    context: Context,
    currentState: MutableState<ScreenState>,
    cameraExecutor: ExecutorService,
    outputDirectory: File,
    classifer: DeseaseClassifier
) {
    lateinit var photoUri: Uri
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    LaunchedEffect(key1 = locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            locationViewModel.getCurrentLocation()
        }else{
            locationPermissions.launchMultiplePermissionRequest()
        }
    }
    val currentLocation = locationViewModel.currentLocation

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    CapstoneProjectTheme() {
        Scaffold(
            bottomBar = {
                if (currentRoute == Screen.Home.route || currentRoute == Screen.Forum.route) {
                    NavigationBottomBar(
                        navController = navController,
                        openDialog = {
                            navController.navigate(Screen.Camera.route)
                        },
                        modifier = modifier.graphicsLayer {

                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                            clip = true
                        }
                    )
                }


            }, modifier = modifier
        ) { padding ->

            Log.d("TAG", "App: ${currentLocation?.latitude ?: 0.0} ${currentLocation?.longitude ?: 0.0}")
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(currentLocation = currentLocation)
                
             /*   ListScreen(onBack = {})*/
                   /* PlantInfoScreen()*/
                }
                composable(Screen.Forum.route) {
                    ForumScreen()
                }
                composable(Screen.Camera.route) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (currentState.value) {
                            is ScreenState.Camera -> {
                                CameraScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    outputDirectory = outputDirectory,
                                    executor = cameraExecutor,
                                    onImageCaptured = { uri ->
                                        photoUri = uri
                                        currentState.value = ScreenState.Photo
                                    },
                                    onError = { Log.e("kilo", "View error:", it) },
                                    onBack = {navController.navigateUp()}
                                )
                            }
                            is ScreenState.Photo -> {
                                ResultScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    photoUri = photoUri,
                                    classifer = classifer,
                                    context = context,
                                    onBack = {
                                        currentState.value = ScreenState.Camera
                                    }
                                )
                            }
                        }
                    }
                }

            }
        }


//

// Sheet content

    }
}

