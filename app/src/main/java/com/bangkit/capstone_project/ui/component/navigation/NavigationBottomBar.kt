package com.bangkit.capstone_project.ui.component.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ui.screen.Screen
import com.bangkit.capstone_project.ui.theme.GreenLight
import com.bangkit.capstone_project.ui.theme.GreenMed


@Composable
fun NavigationBottomBar(
    navController: NavController,openDialog:()->Unit, modifier: Modifier = Modifier
) {
    BottomBar(   modifier = modifier.graphicsLayer {

        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        )
        clip = true
    },openDialog=openDialog) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navItems = listOf(
            NavItem(
                name = "HOME",
                icon = painterResource(id = R.drawable.home),
                route = Screen.Home
            ), NavItem(
                name = "Profile",
                icon = painterResource(id = R.drawable.user_regular),
                route = Screen.Profile
            ),
        )
        BottomBar(openDialog=openDialog) {
            navItems.map { item ->
                NavigationBarItem(icon = {
                    Icon(painter = item.icon, contentDescription = item.name)
                },
                    label = { Text(text = item.name) },
                    selected = currentRoute == item.route.route,
                    onClick = {
                        navController.navigate(item.route.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.White,
                        selectedIconColor = GreenMed,
                        selectedTextColor = GreenMed,
                        unselectedIconColor = GreenLight
                    ),
                    alwaysShowLabel = false)
            }
        }
    }
}