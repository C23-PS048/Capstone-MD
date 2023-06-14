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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.disease.DiseaseResult
import com.bangkit.capstone_project.data.network.disease.DiseaseViewModel
import com.bangkit.capstone_project.data.network.userplant.UserPlantViewModel
import com.bangkit.capstone_project.helper.convertToHyphenated
import com.bangkit.capstone_project.helper.decodeUriAsBitmap
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.InfoScreen
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.ui.theme.GrayLight
import com.bangkit.capstone_project.ui.theme.GreenMed
import com.bangkit.capstone_project.ui.theme.Ivory


@Composable
fun DiagnoseResultScreen(
    onBack: () -> Unit,
    photoUri: Uri,
    modifier: Modifier = Modifier,
    classifier: Map<String, DeseaseClassifier>,
    diseaseViewModel: DiseaseViewModel,
    userPlantViewModel: UserPlantViewModel,
    context: Context,
    slug: String
) {
    var result: List<DeseaseClassifier.Recognition> = listOf()
    lateinit var diseaseClassifier: DeseaseClassifier

    if (slug == "tomato") {
        diseaseClassifier = classifier["tomatoClassifier"]!!
    } else if (slug == "pepper-chili") {
        diseaseClassifier = classifier["chiliClassifier"]!!

    }else if(slug=="cauli-flower"){
        diseaseClassifier = classifier["cauilClassifier"]!!

    }

    val bitmap: Bitmap? = decodeUriAsBitmap(context, photoUri)
    if (bitmap != null) {
        result = diseaseClassifier.recognizeImage(
            bitmap
        )

    }

    CapstoneProjectTheme() {
        Scaffold(
            topBar = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Button"
                            )
                        }
                        Text(
                            text = "Diagnose Result",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Divider(thickness = 3.dp, color = GrayLight)
                }
            },
            modifier = modifier.background(Ivory)
        ) { padding ->
            if (result.isNotEmpty()) {
                val plantName = convertToHyphenated(result[0].title)
                diseaseViewModel.diseaseState.collectAsState(initial = UiState.Loading).value.let { diseaseState ->
                    when (diseaseState) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                            diseaseViewModel.getDisease(plantName)


                        }

                        is UiState.Success -> {

                            val data = diseaseState.data?.diseaseResult
                            Log.d("TAG", "ListScreen: $data")
                            if (data != null) {
                                DiagnoseContent(
                                    result = result,
                                    bitmap = bitmap,
                                    disease = data,

                                    padding = padding,
                                    navigateDetail = { },
                                    onBack = onBack
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
    }
}

@Composable
fun DiagnoseContent(
    result: List<DeseaseClassifier.Recognition>,
    bitmap: Bitmap?,
    disease: DiseaseResult,
    padding: PaddingValues,
    navigateDetail: Any,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(padding)

            .background(Ivory)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (result.isNotEmpty()) {

           Column(modifier.fillMaxWidth().padding(16.dp)) {
               disease.diseaseName?.let {
                   Text(
                       text = it,
                       fontWeight = FontWeight.Medium,
                       fontSize = 24.sp,
                       textAlign = TextAlign.Start,
                       modifier = Modifier.fillMaxWidth()
                   )
               }
               disease.plantName?.let {
                   Text(
                       text = it,
                       fontWeight = FontWeight.Medium,
                       fontSize = 16.sp,
                       color = GreenMed,
                       textAlign = TextAlign.Start,
                       modifier = Modifier.fillMaxWidth()
                   )
               }
           }
            if (bitmap != null) {
                Box(
                    modifier = modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                    )
                }
            }
            Column(
                modifier
                    .fillMaxSize()

                    .background(
                        shape = RoundedCornerShape(
                            topStartPercent = 7,
                            topEndPercent = 7
                        ), color = Color.White
                    ).shadow(elevation = 0.8.dp, shape = RoundedCornerShape(
                        topStartPercent = 15,
                        topEndPercent = 15
                    )).padding(horizontal = 16.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                Column(modifier.fillMaxWidth()) {

                        Text(text = "Penyebab", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)

                    disease.cause?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    }
                }
                Column(modifier.fillMaxWidth()) {


                        Text(
                            text = "Penanganan",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                    disease.care?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,

                            )
                    }
                }
            }
        } else {
            bitmap?.asImageBitmap()?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                        .aspectRatio(1f)

                )
            }
            Text(text = "Not Detected")
        }
    }

    BackHandler() {
        onBack()
    }
}


