package com.bangkit.capstone_project.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.capstone_project.CameraActivity
import com.bangkit.capstone_project.data.LocationViewModel
import com.bangkit.capstone_project.ui.component.navigation.NavigationBottomBar
import com.bangkit.capstone_project.ui.screen.ForumScreen
import com.bangkit.capstone_project.ui.screen.HomeScreen
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
    context: Context
) {

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
                            val intent = Intent(context, CameraActivity::class.java)
                            startActivity(context,intent,null)
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
                }
                composable(Screen.Forum.route) {
                    ForumScreen()
                }

            }
        }


//

// Sheet content

    }
}

