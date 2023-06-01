package com.bangkit.capstone_project.ui.component.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.size.Scale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCards(onClick:()->Unit,modifier: Modifier = Modifier) {
    Card(onClick = onClick, modifier = Modifier
        .width(150.dp)
        .height(200.dp),elevation=CardDefaults.cardElevation(defaultElevation = 4.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center, modifier = modifier.fillMaxSize()) {
            Box(
                modifier = modifier


                    .size(120.dp)
                    .clip(shape = RoundedCornerShape(15))
            ) {
                AsyncImage(
                    model = "https://plantnet.com.au/wp-content/uploads/00.jpg",
                    contentDescription = "Plant Hint",
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier.fillMaxWidth()
                )
            }
           Column(modifier.fillMaxWidth().padding(15.dp,8.dp)) {
               Text(text = "Plant Name", style = MaterialTheme.typography.titleLarge)
               Text(text = "Plant Scientific", style = MaterialTheme.typography.bodySmall)
           }
        }
    }
}

@Preview
@Composable
fun PlantApp() {
    PlantCards(onClick = {})
}