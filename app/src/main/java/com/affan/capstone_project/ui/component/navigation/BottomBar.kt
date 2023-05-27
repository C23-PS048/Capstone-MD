package com.affan.capstone_project.ui.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import com.affan.capstone_project.R
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.affan.capstone_project.ui.theme.GreenMed

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    openDialog:()->Unit,
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color =  Color.White,
        contentColor = Color.White,
        tonalElevation = tonalElevation,
        modifier = modifier.background(
            color = Color.Blue,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
    ) {
        Box(contentAlignment = Alignment.Center,modifier = modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(windowInsets)
                    .height(80.dp)
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = content
            )
            IconButton(onClick =  openDialog, colors = IconButtonDefaults.iconButtonColors(containerColor = GreenMed), modifier = Modifier.size(54.dp).clip(
                RoundedCornerShape(15.dp)
            )) {
                Icon(painter = painterResource(id = R.drawable.leaf), contentDescription = null, tint = Color.White)
            }
        }

    }
}