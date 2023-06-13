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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.capstone_project.data.network.user.UserViewModel
import com.bangkit.capstone_project.helper.decodeUriAsBitmap
import com.bangkit.capstone_project.helper.uriToFile
import com.bangkit.capstone_project.model.UserModel
import com.bangkit.capstone_project.tflite.DeseaseClassifier
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.ui.theme.GrayDark
import com.bangkit.capstone_project.ui.theme.GrayLight
import com.bangkit.capstone_project.ui.theme.Ivory
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel
import java.io.File


@Composable
fun UserResultScreen(
    onBack: () -> Unit,
    photoUri: Uri,

    plantClassifier: DeseaseClassifier,
    userViewModel: UserViewModel,
    preferenceViewModel: PreferenceViewModel,
    context: Context,
    navigateDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    id: Any,
    isBack: Boolean
) {
    val session by preferenceViewModel.getLoginSession().collectAsState(initial = null)
    Log.d("TAG", "UserResultScreen: $isBack")
    val bitmap: Bitmap? = decodeUriAsBitmap(context, photoUri)

    val file = uriToFile(photoUri, context)
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
            UserResultContent(
                session = session,
                bitmap = bitmap,
                padding = padding,
                imageFile = file,
                navigateDetail = navigateDetail,
                userViewModel = userViewModel
            )
        }
        BackHandler() {
            onBack()
        }
    }

    userViewModel.responseState.collectAsState(initial = UiState.Loading).value.let { responseState ->

        when (responseState) {
            is UiState.Loading -> {

            }

            is UiState.Success -> {
                Log.d("Update", "UserResultScreen: ${responseState.data}")


            }

            is UiState.Error -> {
                Log.d("Update", "UserResultScreen: ${responseState.errorMessage}")
            }

            else -> {}
        }

    }
}

@Composable
fun UserResultContent(

    navigateDetail: (String) -> Unit,
    modifier: Modifier = Modifier,

    bitmap: Bitmap?,

    padding: PaddingValues,
    imageFile: File,
    session: UserModel?,
    userViewModel: UserViewModel,

    ) {

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
                        "plant.plantName",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BlackMed
                    )
                    Text(
                        " plant.scientificName",
                        style = MaterialTheme.typography.titleMedium,
                        color = GrayDark
                    )

                }
            }

            Button(onClick = {
                if (session != null) {
                    session.id?.let { session.token?.let { token ->
                        userViewModel.updateUserPhoto(it.toInt(), imageFile,
                            token
                        )
                    } }
                }
            }, modifier.fillMaxWidth()) {
                Text(text = "Informasi Selengkap nya")
            }
        }


    }

}


