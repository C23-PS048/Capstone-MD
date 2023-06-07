package com.bangkit.capstone_project.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.helper.getDaysBetween

import com.bangkit.capstone_project.model.Task
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.buttons.ButtonIcon
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.GrayDark
import com.bangkit.capstone_project.ui.theme.GreenMed
import com.bangkit.capstone_project.ui.theme.Ivory
import com.bangkit.capstone_project.viewmodel.task.TaskViewModel

@Composable
fun OwnedPlantScreen(
    onBack: () -> Unit,
    plantId: Int,
    taskViewModel: TaskViewModel,
    navigateEdit: (Int) -> Unit,
    sendNotification: () -> Unit
) {
    Log.d("TAG", "OwnedPlantScreen: $plantId")


    taskViewModel.detailState.collectAsState(initial = UiState.Loading).value.let { detailState ->
        when (detailState) {
            is UiState.Loading -> {
                taskViewModel.getTaskById(plantId)
            }

            is UiState.Success -> {
                val startTimestamp = System.currentTimeMillis()
                val daysBetween =
                    detailState.data?.nextScheduledDate?.let { getDaysBetween(startTimestamp, it) }
                val location =    detailState.data?.location
                if (daysBetween != null) {
                    if (daysBetween == 0L) {
                        sendNotification()
                    }
                    if (location != null) {
                        OwnedPlantContent(onBack = onBack,daysBetween = daysBetween,location = location, task = detailState.data, navigateEdit=navigateEdit)
                    }
                }
                Log.d("TAG", "HomeScreen: ${detailState.data}")
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun OwnedPlantContent(
    task:Task,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    daysBetween: Long,
    location: String,
    navigateEdit:(Int)->Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Ivory)
    ) {

        Column(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1615213612138-4d1195b1c0e7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=765&q=80",
                    contentDescription = "Image of Your Plant",
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .fillMaxSize()

                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = "nama Tanaman",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    Text(
                        text = "Nama Ilmiah",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = GreenMed),
                        modifier = modifier.size(14.dp)
                    )
                    Text(
                        text = location,
                        style = MaterialTheme.typography.bodySmall,
                        color = GreenMed
                    )


                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(start = 16.dp)
            ) {
                Text(text = "About", style = MaterialTheme.typography.titleLarge)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.heightIn(max = 65.dp)
                        ) {
                            Box(
                                modifier = modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(GrayDark)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.temperature),
                                    contentDescription = null,
                                    tint = BlackMed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                text = "20 - 29 C",
                                style = MaterialTheme.typography.titleSmall,
                                color = BlackMed,

                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.heightIn(max = 65.dp)
                        ) {
                            Box(
                                modifier = modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(GrayDark)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.humidity),
                                    contentDescription = null,
                                    tint = BlackMed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                text = "Water Abundant",
                                style = MaterialTheme.typography.titleSmall,
                                color = BlackMed,

                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.heightIn(max = 65.dp)
                        ) {
                            Box(
                                modifier = modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(GrayDark)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.cloud_sun),
                                    contentDescription = null,
                                    tint = BlackMed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                text = "Light Diffused",
                                style = MaterialTheme.typography.titleSmall,
                                color = BlackMed,

                                textAlign = TextAlign.Center
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.heightIn(max = 65.dp)
                        ) {
                            Box(
                                modifier = modifier
                                    .clip(RoundedCornerShape(15))
                                    .background(GrayDark)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.soil),
                                    contentDescription = null,
                                    tint = BlackMed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                text = "Soil Dry",
                                style = MaterialTheme.typography.titleSmall,
                                color = BlackMed,

                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Box(
                        modifier = modifier
                            .background(
                                GreenMed,
                                shape = RoundedCornerShape(
                                    topStartPercent = 15,
                                    bottomStartPercent = 15
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column() {
                            Text(
                                text = "Next Watering",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = modifier
                                    .size(100.dp)
                                    .border(
                                        border = BorderStroke(5.dp, Color.White),
                                        CircleShape
                                    )
                                    .padding(16.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = daysBetween.toString(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Days",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back To Home Button",

                )
        }
        Box(
            modifier = modifier
                .align(Alignment.TopEnd)
        ) {
            var expanded by remember { mutableStateOf(false) }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = { navigateEdit(task.id)},
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    })
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = { /* Handle settings! */ },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    })
            }

        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            ButtonIcon(
                onClick = { /*TODO*/ },
                title = "Something Wrong? scan here ",
                description = "Button To scan Your Plant",
                icon = painterResource(id = R.drawable.scanner),
                corner = 15,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
        }

    }
}

@Preview
@Composable
fun previewOwned() {
    OwnedPlantContent(onBack = {}, daysBetween = 7, location = "location", navigateEdit = {1}, task = Task(1,"ad",1231313131L,3,1231313131L,1231313131L))
}

