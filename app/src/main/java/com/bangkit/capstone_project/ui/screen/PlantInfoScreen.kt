package com.bangkit.capstone_project.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.capstone_project.ui.theme.GreenDark


@Composable
fun PlantInfoScreen() {


    Scaffold(topBar = {
        IconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Return To Login")
        }
    }, modifier = Modifier.background(GreenDark)) {
        Column(modifier = Modifier.padding(it)) {

        }
    }


}


@Preview
@Composable
fun PlantInfo() {
    PlantInfoScreen()
}