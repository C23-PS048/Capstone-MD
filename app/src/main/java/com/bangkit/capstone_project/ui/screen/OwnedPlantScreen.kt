package com.bangkit.capstone_project.ui.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.data.network.userplant.UserPlant
import com.bangkit.capstone_project.data.network.userplant.UserPlantViewModel
import com.bangkit.capstone_project.helper.calculateScheduleDates
import com.bangkit.capstone_project.helper.convertHoursToDays
import com.bangkit.capstone_project.helper.getTimeBetween
import com.bangkit.capstone_project.model.UserModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.InfoScreen
import com.bangkit.capstone_project.ui.component.buttons.ButtonIcon
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.GrayDark
import com.bangkit.capstone_project.ui.theme.GreenDark
import com.bangkit.capstone_project.ui.theme.GreenLight
import com.bangkit.capstone_project.ui.theme.GreenMed
import com.bangkit.capstone_project.ui.theme.Ivory
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel

@Composable
fun OwnedPlantScreen(
    onBack: () -> Unit,
    plantId: Int,
    plantViewModel: PlantViewModel,
    navigateEdit: (Int) -> Unit,
    sendNotification: () -> Unit,
    userPlantViewModel: UserPlantViewModel,
    prefViewModel: PreferenceViewModel,
    navController: NavHostController,
    showToast: (String) -> Unit
) {
    val session by prefViewModel.getLoginSession().collectAsState(initial = null)
    val task: MutableState<UserPlant?> = remember {
        mutableStateOf(null)
    }
    val planData: MutableState<PlantResult?> = remember {
        mutableStateOf(null)
    }

    DisposableEffect(Unit) {
        onDispose {
            userPlantViewModel.resetData()

        }
    }
    userPlantViewModel.responseState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {

            }

            is UiState.Success -> {
                uiState.data?.let { showToast(it.message) }
            }


            is UiState.Error -> {
                showToast(uiState.errorMessage)
            }

            else -> {}
        }
    }
    userPlantViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                session?.token?.let { userPlantViewModel.getPlant(plantId, it) }
            }

            is UiState.Success -> {
                task.value = uiState.data?.userPlant
            }


            is UiState.Error -> {showToast(uiState.errorMessage)}

        }
    }
    plantViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                task.value?.plantId.let {
                    if (it != null) {
                        plantViewModel.getAll()
                    }
                }


            }

            is UiState.Success -> {
                uiState.data?.plantList?.forEach {
                    if (it.id == task.value?.plantId) {
                        planData.value = it
                    }
                }
                val startTimestamp = System.currentTimeMillis()
                val timeBetween =
                    task.value?.nextScheduledDate?.let {
                        getTimeBetween(
                            startTimestamp,
                            it.toLong()
                        )
                    }
                val location = task.value?.location

                if (timeBetween == 0L) {
                    sendNotification()
                }
                if (location != null) {
                    planData.value?.let { data ->
                        OwnedPlantContent(
                            navController = navController,
                            onBack = onBack,
                            session = session,
                            timeBetween = timeBetween,
                            userPlantViewModel = userPlantViewModel,
                            location = location,
                            task = task.value,
                            data = data,
                            navigateEdit = navigateEdit
                        )
                    }
                }

            }

            is UiState.Error -> {
                InfoScreen(text = uiState.errorMessage)
                showToast(uiState.errorMessage)
            }

        }
    }


}

@Composable
fun OwnedPlantContent(

    task: UserPlant?,
    data: PlantResult,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    timeBetween: Long?,
    location: String,
    navigateEdit: (Int) -> Unit,
    userPlantViewModel: UserPlantViewModel,

    session: UserModel?,
    navController: NavHostController
) {
    val openDialog = remember { mutableStateOf(false) }
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
                    model = data.image[0],
                    contentDescription = stringResource(R.string.image_desc),
                    contentScale = ContentScale.FillBounds,
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
                        text = data.plantName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    Text(
                        text = data.scientificName,
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
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleLarge
                )
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
                                text = data.temperature,
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
                                text = data.wateringFrequency,
                                style = MaterialTheme.typography.titleSmall,
                                color = BlackMed,

                                textAlign = TextAlign.Center
                            )
                        }

                    }

                    Box(
                        modifier = modifier
                            .width(150.dp)
                            .background(
                                GreenMed,
                                shape = RoundedCornerShape(
                                    topStartPercent = 15,
                                    bottomStartPercent = 15
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column(
                            modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (timeBetween != null) {
                                if (timeBetween <= 0) {
                                    Text(
                                        text = stringResource(R.string.sudah_menyiram),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.fillMaxWidth()
                                    )

                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = modifier
                                            .size(100.dp)
                                            .border(
                                                border = BorderStroke(
                                                    5.dp,
                                                    Color.White
                                                ),
                                                CircleShape
                                            )

                                    ) {
                                        Button(
                                            onClick = {
                                                openDialog.value = !openDialog.value
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = GreenLight),
                                            modifier = modifier.fillMaxSize()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = GreenDark, modifier =
                                                modifier.size(30.dp)
                                            )
                                        }
                                    }


                                } else {
                                    Text(
                                        text = stringResource(R.string.watering_tag),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = modifier.fillMaxWidth()
                                    )
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = modifier
                                            .size(100.dp)
                                            .border(
                                                border = BorderStroke(
                                                    5.dp,
                                                    Color.White
                                                ),
                                                CircleShape
                                            )
                                            .padding(16.dp)
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                            Text(
                                                text = if (timeBetween > 24) convertHoursToDays(
                                                    timeBetween
                                                ).toString() else timeBetween.toString(),
                                                style = MaterialTheme.typography.titleLarge,
                                                color = Color.White,
                                                textAlign = TextAlign.Center
                                            )
                                            Text(
                                                text = if (timeBetween > 24) stringResource(
                                                    R.string.day_tag
                                                ) else stringResource(
                                                    R.string.hour_tag
                                                ),
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
            }
        }
        IconButton(
            onClick = onBack,
            colors = IconButtonDefaults.iconButtonColors(containerColor = BlackMed.copy(alpha = 0.1f))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back To Home Button",

                tint = Color.White
            )
        }
        Box(
            modifier = modifier
                .align(Alignment.TopEnd)
        ) {
            var expanded by remember { mutableStateOf(false) }

            IconButton(
                onClick = { expanded = !expanded },
                colors = IconButtonDefaults.iconButtonColors(containerColor = BlackMed.copy(alpha = 0.1f))
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {
                        task?.id?.let { navigateEdit(it) }
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    })

            }

        }
        if (data.slug == "tomato" || data.slug == "pepper-chili") {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                ButtonIcon(
                    onClick = {
                        navController.navigate(Screen.DiseaseCam.createRoute(data.slug))
                    },
                    title = "Check Kesehatan Tanamanu",
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

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {

                openDialog.value = false
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.plant_care),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = modifier.size(32.dp)
                )
            },
            title = {
                Text(text = "Sudah Menyiram?")
            },
            text = {
                Text(
                    "Pastikan anda sudah menyiram tanaman anda dengan baik"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (session != null) {
                            session.token?.let {
                                if (task != null) {
                                    updateSchedule(
                                        task,
                                        it,
                                        userPlantViewModel
                                    )
                                }
                            }
                        }
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}


fun updateSchedule(
    task: UserPlant,
    token: String,
    userPlantViewModel: UserPlantViewModel,
) {

    val freq = task.frequency
    val lastDate = task.lastScheduledDate

    val (lastScheduledDate, nextScheduledDate) = calculateScheduleDates(lastDate.toLong(), freq)






    userPlantViewModel.updateTask(
        location = task.location,
        disease = task.disease,
        startDate = task.startDate,
        lastScheduledDate = lastScheduledDate.toString(),
        nextScheduledDate = nextScheduledDate.toString(),
        frequency = task.frequency.toString(),
        plantId = task.plantId.toString(),
        userId = task.userId.toString(),
        token = token,
        id = task.id.toString()
    )
}



