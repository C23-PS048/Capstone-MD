package com.bangkit.capstone_project.ui.screen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.helper.convertMillisToDateString
import com.bangkit.capstone_project.ui.component.buttons.ButtonIcon
import com.bangkit.capstone_project.ui.component.cards.ScheduleInput
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.GrayLight
import com.bangkit.capstone_project.ui.theme.GreenDark


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun PlantInfoScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {



    val openWeatherDate = remember { mutableStateOf(false) }
    val openWeatherRepeat = remember { mutableStateOf(false) }
    val dateWeatherState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Return To Login",
                        tint = Color.White
                    )
                }
            },
            containerColor = GreenDark,
            modifier = modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column() {
                            Text(
                                "Plant Name",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                "Scientific",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.LightGray
                            )
                        }
                        Box(
                            modifier = modifier
                                .width(120.dp)
                                .height(150.dp)
                                .clip(shape = RoundedCornerShape(15))
                        ) {
                            AsyncImage(
                                model = "https://plantnet.com.au/wp-content/uploads/00.jpg",
                                contentDescription = "Plant Hint",
                                contentScale = ContentScale.FillWidth,
                                modifier = modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                item {
                    Box(
                        modifier = modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    topStartPercent = 5,
                                    topEndPercent = 5
                                )
                            )
                            .background(Color.White)
                            .padding(16.dp, 32.dp)
                            .padding(bottom = 64.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "About", style = MaterialTheme.typography.titleLarge)
                                Text(
                                    text = "vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt eget nullam non nisi est sit amet facilisis magna etiam tempor orci eu lobortis elementum",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.widthIn(max = 65.dp)
                                ) {
                                    Box(
                                        modifier = modifier
                                            .clip(RoundedCornerShape(15))
                                            .background(GrayLight)
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
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.widthIn(max = 65.dp)
                                ) {
                                    Box(
                                        modifier = modifier
                                            .clip(RoundedCornerShape(15))
                                            .background(GrayLight)
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
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.widthIn(max = 65.dp)
                                ) {
                                    Box(
                                        modifier = modifier
                                            .clip(RoundedCornerShape(15))
                                            .background(GrayLight)
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
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.widthIn(max = 65.dp)
                                ) {
                                    Box(
                                        modifier = modifier
                                            .clip(RoundedCornerShape(15))
                                            .background(GrayLight)
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
                                        textAlign = TextAlign.Center
                                    )
                                }
                                // Repeat the other three columns here

                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Tips", style = MaterialTheme.typography.titleLarge)
                                Text(
                                    text = "vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt eget nullam non nisi est sit amet facilisis magna etiam tempor orci eu lobortis elementum",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Tips", style = MaterialTheme.typography.titleLarge)
                                LazyRow(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(5) {
                                        Box(
                                            modifier = modifier
                                                .width(120.dp)
                                                .height(150.dp)
                                                .clip(shape = RoundedCornerShape(15))
                                        ) {
                                            AsyncImage(
                                                model = "https://plantnet.com.au/wp-content/uploads/00.jpg",
                                                contentDescription = "Plant Hint",
                                                contentScale = ContentScale.FillWidth,
                                                modifier = modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                    // Add more items to the LazyRow here if needed
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            ButtonIcon(
                onClick = {},
                title = "Add Plant",
                description = "Button to add your plant",
                icon = painterResource(id = R.drawable.leaf),
                corner = 15,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }


        if (openWeatherDate.value) {
            val confirmEnabled = derivedStateOf { dateWeatherState.selectedDateMillis != null }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                DatePickerDialog(
                    onDismissRequest = {

                        openWeatherDate.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openWeatherDate.value = false
                            },
                            enabled = confirmEnabled.value
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openWeatherDate.value = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = dateWeatherState)
                }
            }
        }
        if (openWeatherRepeat.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openWeatherRepeat.value = false
                }
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "This area typically contains the supportive text " +
                                    "which presents the details regarding the Dialog's purpose.",
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        TextButton(
                            onClick = {
                                openWeatherRepeat.value = false
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}

