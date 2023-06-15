package com.bangkit.capstone_project.ui.component.cards

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.data.network.plant.PlantResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCards(onClick: () -> Unit, modifier: Modifier = Modifier, data: PlantResult) {
    Log.d("TAG", "PlantCards: ${data.image}")
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
                    model = data.image[0],
                    contentDescription = "Plant Hint",
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxWidth()
                )
            }
           Column(
               modifier
                   .fillMaxWidth()
                   .padding(15.dp, 8.dp)) {
               Text(text = data.plantName, style = MaterialTheme.typography.titleLarge)
               Text(text = data.scientificName, style = MaterialTheme.typography.bodySmall, lineHeight = 11.sp, overflow = TextOverflow.Ellipsis, modifier = modifier.fillMaxWidth())
           }
        }
    }
}

