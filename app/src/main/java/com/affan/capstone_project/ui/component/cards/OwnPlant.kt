package com.affan.capstone_project.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
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
import com.affan.capstone_project.ui.theme.GreenMed
import com.affan.capstone_project.ui.theme.RedDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnPlantCard(isSick:Boolean,modifier:Modifier=Modifier) {
    Card(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
        Row(modifier = modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier.padding(4.dp)) {
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
            Image(imageVector = Icons.Default.Home, contentDescription = null,modifier.size(100.dp))
        }
    }
}

@Preview
@Composable
fun OwnPlantPrev() {
    OwnPlantCard(false)
}