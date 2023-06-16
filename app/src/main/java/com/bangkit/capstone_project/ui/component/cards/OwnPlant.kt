package com.bangkit.capstone_project.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ui.theme.GreenMed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnPlantCard(
    location: String,
    modifier: Modifier = Modifier,
    navigatetoOwned: () -> Unit,
    id: Int,
    plantName: String,
    plantImage: List<String>,
    plantScientifi: String
) {
    Card(onClick =  navigatetoOwned , modifier = modifier
        .fillMaxWidth()
        .height(110.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) ) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier.padding(16.dp)) {
                Text(text = plantName, style = MaterialTheme.typography.titleLarge, color = Color.Black)
                Text(text = plantScientifi,style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                        Image(painter = painterResource(id = R.drawable.marker), contentDescription = null, colorFilter = ColorFilter.tint(color = GreenMed), modifier = modifier.size(14.dp) )
                        Text(text = location,style = MaterialTheme.typography.bodySmall, color = GreenMed)


                }
            }
            Box(modifier.fillMaxHeight().width(110.dp)) {
                AsyncImage(model = plantImage[0], contentDescription = null,modifier.fillMaxHeight(), contentScale = ContentScale.FillHeight)
            }
        }
    }

}

@Preview
@Composable
fun OwnPlantPrev() {
    OwnPlantCard(
        "sdw",
        navigatetoOwned = {},
        id = 2,
        plantName = "plantResult",
        plantImage = listOf(""),
        plantScientifi = "plantResult.scientificName"
    )
}