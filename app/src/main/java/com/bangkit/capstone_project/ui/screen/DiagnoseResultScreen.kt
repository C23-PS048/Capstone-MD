package com.bangkit.capstone_project.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.capstone_project.helper.decodeUriAsBitmap
import com.bangkit.capstone_project.helper.rotateImage
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.ui.theme.GrayLight



@Composable
fun DiagnoseResultScreen(
    onBack: () -> Unit,
    photoUri: Uri,
    modifier: Modifier = Modifier,
    classifer: DeseaseClassifier,
    context: Context
) {
    var result: List<DeseaseClassifier.Recognition> = listOf()

    val bitmap: Bitmap? = decodeUriAsBitmap(context, photoUri)?.let { rotateImage(it) }
    if (bitmap != null) {
        result = classifer.recognizeImage(
            bitmap
        )

    }
    Log.d("TAG", "ResultScreen: ${result}")
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
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (result.isNotEmpty()) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .width(300.dp)
                                .height(300.dp)
                                .aspectRatio(1f)

                        )
                    }
                    Text(text = result[0].title)
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
        }
        BackHandler() {
            onBack()
        }
    }
}

