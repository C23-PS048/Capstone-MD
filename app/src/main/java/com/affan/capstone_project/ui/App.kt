package com.affan.capstone_project.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.affan.capstone_project.ui.component.navigation.NavigationBottomBar
import com.affan.capstone_project.ui.screen.ForumScreen
import com.affan.capstone_project.ui.screen.HomeScreen
import com.affan.capstone_project.ui.screen.Screen
import com.affan.capstone_project.ui.theme.CapstoneProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()



    CapstoneProjectTheme() {
        Scaffold(
            bottomBar = {

                NavigationBottomBar(
                    navController = navController,
                    openDialog = {
                    },
                    modifier = modifier.graphicsLayer {

                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                        clip = true
                    }
                )


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

