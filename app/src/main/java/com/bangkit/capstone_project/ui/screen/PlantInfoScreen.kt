package com.bangkit.capstone_project.ui.screen


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.buttons.ButtonIcon
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.GrayLight
import com.bangkit.capstone_project.ui.theme.GreenDark


@SuppressLint("UnrememberedMutableState")

@Composable

fun PlantInfoScreen(
    navigateTask: () -> Unit,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    slug: String,
    token: String?,
    plantViewModel: PlantViewModel
) {


    plantViewModel.plantState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                token.let { it1 ->
                    if (it1 != null) {
                        plantViewModel.getPlant(slug, it1)
                    }
                }
            }

            is UiState.Success -> {

                val data = uiState.data?.plantResult
                Log.d("TAG", "ListScreen: $data")
                if (data != null) {
                    DetailContent(
                        navigateTask = navigateTask,
                        modifier = modifier, onBack = onBack, plant = data
                    )
                }

            }

            is UiState.Error -> {}

        }
    }


}

@Composable
fun DetailContent(
    navigateTask: () -> Unit,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    plant: PlantResult
) {

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
                                plant.plantName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                plant.scientificName,
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
                                Text(
                                    text = "Tentang",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = plant.description,
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
                                        text = plant.temperature,
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
                                        text = plant.wateringFrequency,
                                        style = MaterialTheme.typography.titleSmall,
                                        textAlign = TextAlign.Center
                                    )
                                }


                                // Repeat the other three columns here

                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Tips Menyiram", style = MaterialTheme.typography.titleLarge)
                                Text(
                                    text = plant.wateringTips,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Tips Menanam", style = MaterialTheme.typography.titleLarge)
                                Text(
                                    text = plant.plantTips,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Gambar", style = MaterialTheme.typography.titleLarge)
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
                onClick = navigateTask,
                title = "Add Plant",
                description = "Button to add your plant",
                icon = painterResource(id = R.drawable.leaf),
                corner = 15,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }


    }
}