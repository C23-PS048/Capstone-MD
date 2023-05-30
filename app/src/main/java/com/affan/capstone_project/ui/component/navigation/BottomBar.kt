package com.affan.capstone_project.ui.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.affan.capstone_project.R
import com.affan.capstone_project.ui.screen.Screen
import com.affan.capstone_project.ui.theme.GreenLight
import com.affan.capstone_project.ui.theme.GreenMed

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    openDialog: () -> Unit,
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = Color.White,
        contentColor = Color.White,
        tonalElevation = tonalElevation,

        modifier = modifier.graphicsLayer {

            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp
            )
            clip = true
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,modifier = modifier.graphicsLayer {

                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
                clip = true
            }
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(windowInsets)
                    .height(80.dp)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
            Button(
                onClick =  openDialog,
                modifier = Modifier.size(56.dp),

                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenMed
                ),
                contentPadding = PaddingValues(4.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.leaf),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(36.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun Prev() {
    BottomBar(modifier = Modifier, openDialog = {}) {



        val navItems = listOf(
            NavItem(
                name = "HOME",
                icon = painterResource(id = R.drawable.home),
                route = Screen.Home
            ),
            NavItem(
                name = "HOME",
                icon = painterResource(id = R.drawable.user_regular),
                route = Screen.Forum
            ),
        )
        BottomBar(openDialog = { }) {
            navItems.map { item ->
                NavigationBarItem(
                    icon = {
                        Icon(painter = item.icon, contentDescription = item.name)
                    },
                    label = { Text(text = item.name) },
                    selected = true,
                    onClick = {

                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White,
                        selectedIconColor = GreenMed,
                        selectedTextColor = GreenMed,
                        unselectedIconColor = GreenLight
                    ),
                    alwaysShowLabel = false
                )
            }
        }
    }
}