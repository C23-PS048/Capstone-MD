package com.affan.capstone_project.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.affan.capstone_project.CameraActivity
import com.affan.capstone_project.ui.component.navigation.NavigationBottomBar
import com.affan.capstone_project.ui.screen.ForumScreen
import com.affan.capstone_project.ui.screen.HomeScreen
import com.affan.capstone_project.ui.screen.Screen
import com.affan.capstone_project.ui.theme.CapstoneProjectTheme
import java.io.File
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    outputDirectory: File,
    cameraExecutor: ExecutorService,
    handleImageCapture: (Uri) -> Unit,
    context: Context
) {


    var shouldShowCamera: MutableState<Boolean> = remember { mutableStateOf(false) }

    lateinit var photoUri: Uri
    var shouldShowPhoto: MutableState<Boolean> = remember { mutableStateOf(false) }
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


            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
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

