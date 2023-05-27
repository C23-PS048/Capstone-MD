package com.affan.capstone_project.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherCards(modifier:Modifier = Modifier) {
    Card(onClick = { /*TODO*/ }, modifier = modifier) {
        Row(modifier = modifier.padding(16.dp)) {
            Image(imageVector = Icons.Default.Home, contentDescription = null,modifier.size(100.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier.padding(4.dp)) {
                Text(text = "Sunny | 28 C", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                Text(text = "Jakarta, Indonesia",style = MaterialTheme.typography.titleMedium, color = Color.Black)
                Text(text = "This Weather is Perfect for Your Plants",style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
        }
    }
}

@Preview
@Composable
fun WeatherPrev() {
    WeatherCards()
}