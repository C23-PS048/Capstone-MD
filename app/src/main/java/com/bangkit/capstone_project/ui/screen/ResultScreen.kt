package com.bangkit.capstone_project.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.helper.convertToHyphenated
import com.bangkit.capstone_project.helper.decodeUriAsBitmap
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.InfoScreen
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.ui.theme.GrayDark
import com.bangkit.capstone_project.ui.theme.GrayLight
import com.bangkit.capstone_project.ui.theme.Ivory


@Composable
fun ResultScreen(
    onBack: () -> Unit,
    photoUri: Uri,

    plantClassifier: DeseaseClassifier,
    plantViewModel: PlantViewModel,
    context: Context,
    navigateDetail: (String) -> Unit,
    modifier: Modifier = Modifier,

) {
    var result: List<DeseaseClassifier.Recognition> = listOf()

    val bitmap: Bitmap? = decodeUriAsBitmap(context, photoUri)

    CapstoneProjectTheme() {
        Scaffold(
            topBar = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 16.dp)
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Button"
                            )
                        }
                        Text(
                            text = "Scan Result",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Divider(thickness = 3.dp, color = GrayLight)
                }
            },
            modifier = modifier
        ) { padding ->


            if (bitmap != null) {
                result = plantClassifier.recognizeImage(
                    bitmap
                )

            }
            if (result.isNotEmpty()) {
                val plantName = convertToHyphenated(result[0].title)
                plantViewModel.plantState.collectAsState(initial = UiState.Loading).value.let { plantState ->
                    when (plantState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                            plantViewModel.getPlant(plantName)


                        }

                        is UiState.Success -> {

                            val data = plantState.data?.plantResult
                            Log.d("TAG", "ListScreen: $data")
                            if (data != null) {
                                ResultContent(
                                    result = result,
                                    bitmap = bitmap,
                                    plant = data,

                                    padding = padding,
                                    navigateDetail = navigateDetail
                                )

                            }


                        }

                        is UiState.Error -> {}

                    }
                }

                Log.d("TAG", "ResultScreen: ${result}")
            } else {
                InfoScreen(modifier = modifier.padding(padding))
            }

        }
        BackHandler() {
            onBack()
        }
    }
}

@Composable
fun ResultContent(

    navigateDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    result: List<DeseaseClassifier.Recognition>,
    bitmap: Bitmap?,
    plant: PlantResult,
    padding: PaddingValues,

    ) {
    if (result.isNotEmpty()) {
        if (bitmap != null) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Ivory),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier
                            .size(300.dp)
                            .clip(shape = RoundedCornerShape(15))
                    ) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                        )
                    }

                    Column(
                        modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            plant.plantName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BlackMed
                        )
                        Text(
                            plant.scientificName,
                            style = MaterialTheme.typography.titleMedium,
                            color = GrayDark
                        )

                    }
                }

                Button(onClick = { navigateDetail(plant.slug) }, modifier.fillMaxWidth()) {
                    Text(text = "Informasi Selengkap nya")
                }
            }


        }
    }

}


@Preview
@Composable
fun Preview(modifier: Modifier = Modifier) {

}