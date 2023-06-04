package com.bangkit.capstone_project.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.affan.storyapp_compose.ui.screen.register.RegisterScreen
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ScreenState
import com.bangkit.capstone_project.data.LocationViewModel
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.component.buttons.ButtonIcon
import com.bangkit.capstone_project.ui.component.navigation.NavigationBottomBar
import com.bangkit.capstone_project.ui.screen.CameraScreen
import com.bangkit.capstone_project.ui.screen.ForumScreen
import com.bangkit.capstone_project.ui.screen.HomeScreen
import com.bangkit.capstone_project.ui.screen.ListScreen
import com.bangkit.capstone_project.ui.screen.LoginScreen
import com.bangkit.capstone_project.ui.screen.PlantInfoScreen
import com.bangkit.capstone_project.ui.screen.ResultScreen
import com.bangkit.capstone_project.ui.screen.Screen
import com.bangkit.capstone_project.ui.screen.TaskScreen
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.viewmodel.task.TaskViewModel
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
    classifer: DeseaseClassifier,
    taskViewModel: TaskViewModel
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
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
        } else {
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

                            openBottomSheet = !openBottomSheet
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

            Log.d(
                "TAG",
                "App: ${currentLocation?.latitude ?: 0.0} ${currentLocation?.longitude ?: 0.0}"
            )
            NavHost(
                navController = navController,
                startDestination = Screen.Login.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(currentLocation = currentLocation, taskViewModel = taskViewModel)
                    /*Date()*/

                }

                composable(Screen.Login.route) {
                    LoginScreen(navigateMain = { navController.navigate(Screen.Home.route) }, navigateRegis = {navController.navigate(Screen.Register.route)})

                    }
                composable(Screen.Register.route) {
                    RegisterScreen(navigateLogin = { navController.navigate(Screen.Login.route) }, onBack = {navController.navigateUp()})

                }


                composable(Screen.Task.route) {
                        TaskScreen(onBack = { navController.navigateUp() }, taskViewModel = taskViewModel)

                    }

                    composable(Screen.Forum.route) {
                        ForumScreen()
                    }
                    composable(Screen.ListPlant.route) {
                        ListScreen(
                            onBack = { navController.navigateUp() },
                            onclick = { navController.navigate(Screen.DetailPlant.route) })
                    }
                    composable(Screen.DetailPlant.route) {
                        PlantInfoScreen(
                            navigateTask = { navController.navigate(Screen.Task.route) },
                            onBack = { navController.navigateUp() })
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
                                        onBack = { navController.navigateUp() }
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
            if (openBottomSheet) {
                val windowInsets = if (edgeToEdgeEnabled)
                    WindowInsets(0) else BottomSheetDefaults.windowInsets

                ModalBottomSheet(
                    onDismissRequest = { openBottomSheet = false },
                    sheetState = bottomSheetState,
                    windowInsets = windowInsets,
                    modifier = Modifier.height(200.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 32.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Text(
                            text = "Add Your Plant",
                            textAlign = TextAlign.Center,
                            modifier = modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(18.dp)
                        ) {
                            ButtonIcon(
                                onClick = {
                                    navController.navigate(Screen.Camera.route)
                                    openBottomSheet = false
                                },
                                title = "Already Planted",
                                description = "button",
                                icon = painterResource(
                                    id = R.drawable.scanner
                                ),
                                corner = 15,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            ButtonIcon(
                                onClick = {
                                    navController.navigate(Screen.ListPlant.route)
                                    openBottomSheet = false
                                },
                                title = "Planning To Plant",
                                description = "button",
                                icon = painterResource(
                                    id = R.drawable.leaf
                                ),
                                corner = 15,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }


//

// Sheet content

            }
        }
    }

