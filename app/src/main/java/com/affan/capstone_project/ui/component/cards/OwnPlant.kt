package com.affan.capstone_project.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.affan.capstone_project.ui.theme.GreenMed
import com.affan.capstone_project.ui.theme.RedDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnPlantCard(isSick:Boolean,modifier:Modifier=Modifier) {
    Card(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth().height(110.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) ) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier.padding(16.dp)) {
                Text(text = "nama Tanaman", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                Text(text = "Nama Ilmiah",style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (isSick){

                    Image(imageVector = Icons.Default.Warning, contentDescription = null, colorFilter = ColorFilter.tint(color = RedDark), modifier = modifier.size(14.dp) )
                Text(text = "Needs Attention",style = MaterialTheme.typography.bodySmall, color = RedDark)
                    }else{
                        Image(imageVector = Icons.Default.Favorite, contentDescription = null, colorFilter = ColorFilter.tint(color = GreenMed), modifier = modifier.size(14.dp) )
                        Text(text = "Needs Attention",style = MaterialTheme.typography.bodySmall, color = GreenMed)
                    }

                }
            }
            AsyncImage(model = "https://images.unsplash.com/photo-1615213612138-4d1195b1c0e7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=765&q=80", contentDescription = null,modifier.fillMaxHeight())
        }
    }
}

@Preview
@Composable
fun OwnPlantPrev() {
    OwnPlantCard(false)
}