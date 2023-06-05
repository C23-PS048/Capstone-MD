package com.bangkit.capstone_project.ui.component.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme


@Composable
fun ScheduleInput(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    selectDialog: Boolean,
    isSelectOpen: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    Date: String,
    Selected: String
) {

    Card(modifier = modifier

        .fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Weather")
            Row(modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onValueChange(!value)
                }
                , horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Date")
                Row() {
                    Text(text = Date)

                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                }


            }
            Divider()
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        isSelectOpen(!selectDialog)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Repeat")
                Row() {
                    Text(text =Selected)
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                }


            }
        }
    }
}

@Preview
@Composable
fun InputPrev() {
    CapstoneProjectTheme {
        ScheduleInput(false, { true }, false, { true }, Date = "12131", Selected = "selectedOption")
    }
}