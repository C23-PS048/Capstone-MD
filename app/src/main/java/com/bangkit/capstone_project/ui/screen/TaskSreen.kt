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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.BuildConfig
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.data.network.userplant.UserPlantViewModel
import com.bangkit.capstone_project.helper.calculateScheduleDates
import com.bangkit.capstone_project.helper.convertMillisToDateString
import com.bangkit.capstone_project.model.Frequency
import com.bangkit.capstone_project.model.UserModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.cards.ScheduleInput
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.ui.theme.GrayLight
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel
import com.bangkit.capstone_project.viewmodel.task.TaskViewModel


@Composable
fun TaskScreen(
    onBack: () -> Unit,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    plantViewModel: PlantViewModel,
    preferenceViewModel: PreferenceViewModel,
    userPlantViewModel: UserPlantViewModel,
    plantId: String,
    showToast: (String) -> Unit
) {
    val session by preferenceViewModel.getLoginSession().collectAsState(initial = null)
    CapstoneProjectTheme() {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            userPlantViewModel.responseState.collectAsState(initial = null).value.let { responseState ->
                when (responseState) {
                    is UiState.Loading -> {


                        CircularProgressIndicator(modifier.align(Alignment.Center))


                    }

                    is UiState.Success -> {

                        showToast("Data Tersimpan")
                        userPlantViewModel.resetResponseState()
                        navigateHome()
                    }

                    is UiState.Error -> {
                        showToast(responseState.errorMessage)
                    }

                    else -> {}
                }

            }
            plantViewModel.plantState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {


                        plantViewModel.getPlant(plantId)


                    }

                    is UiState.Success -> {

                        val data = uiState.data?.plantResult
                        Log.d("TAG", "ListScreen: $data")
                        if (data != null) {
                            TaskContent(
                                onBack = onBack,
                                session = session,
                                navigateHome = navigateHome,
                                taskViewModel = taskViewModel,
                                modifier = modifier,
                                plant = data,
                                userPlantViewModel = userPlantViewModel
                            )
                        }

                    }

                    is UiState.Error -> {
                        showToast(uiState.errorMessage)
                    }

                }

            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    onBack: () -> Unit,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    plant: PlantResult,
    userPlantViewModel: UserPlantViewModel,
    session: UserModel?,
) {
    val openWeatherDate = remember { mutableStateOf(false) }
    val openWeatherRepeat = remember { mutableStateOf(false) }
    val radioOptions = listOf(
        Frequency("Once a Month", 1),
        Frequency("Once a Week", 2),
        Frequency("Every 3 Days", 3),
        Frequency("Every Day", 4),

        )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val textLocation = mutableStateOf("")
    val dateWeatherState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

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
                        text = "Schedule Your Plant",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Divider(thickness = 3.dp, color = GrayLight)
            }
        },
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    modifier = modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
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
                                color = BlackMed
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
                            Log.d(
                                "TAG",
                                "TaskContent: ${BuildConfig.BASE_URL + plant.image[0]}"
                            )
                            AsyncImage(
                                model = plant.image[0],
                                contentDescription = "Plant Hint",
                                contentScale = ContentScale.FillWidth,
                                modifier = modifier.fillMaxWidth()
                            )
                        }
                    }
                }





                dateWeatherState.selectedDateMillis?.let {
                    convertMillisToDateString(it)
                }?.let {
                    ScheduleInput(
                        value = openWeatherDate.value,
                        onValueChange = { openWeatherDate.value = it },
                        selectDialog = openWeatherRepeat.value,
                        isSelectOpen = { openWeatherRepeat.value = it },
                        Date = it,
                        Selected = selectedOption.label,
                        modifier = modifier.background(Color.White)
                    )
                }

                OutlinedTextField(
                    value = textLocation.value,
                    onValueChange = { textLocation.value = it },
                    label = { Text("Plant Location") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.marker),
                            contentDescription = " Location Input"
                        )
                    },
                    modifier = modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (session != null) {
                            session.token?.let {

                                session.id?.let { it1 ->
                                    saveData(
                                        location = textLocation.value,
                                        frequency = selectedOption.frequency,
                                        startDate = dateWeatherState.selectedDateMillis,

                                        plantId = plant.id,
                                        userId = it1.toInt(),
                                        token = it,
                                        userPlantViewModel = userPlantViewModel
                                    )
                                }

                            }
                        }
                        /*   navigateHome()*/
                    },
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = "Input")
                }
            }
        }
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
        val confirmEnabled = derivedStateOf { dateWeatherState.selectedDateMillis != null }
        AlertDialog(
            onDismissRequest = {

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

// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
                Column(modifier.fillMaxWidth()) {
                    Column(Modifier.selectableGroup()) {
                        radioOptions.forEach { text ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .selectable(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) },
                                        role = Role.RadioButton
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = null // null recommended for accessibility with screenreaders
                                )
                                Text(
                                    text = text.label,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }

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

fun saveData(
    frequency: Int, startDate: Long?, location: String, userPlantViewModel: UserPlantViewModel,
    plantId: Int,
    userId: Int,
    token: String
) {

    startDate?.let {
        val (lastDate, NextDate) = calculateScheduleDates(
            frequency = frequency,
            startDate = startDate
        )

        userPlantViewModel.saveUserPlant(
            location = location,
            disease = "healthy",
            startDate = startDate.toString(),
            lastScheduledDate = lastDate.toString(),
            nextScheduledDate = NextDate.toString(),
            frequency = frequency.toString(),
            plantId = plantId.toString(),
            userId = userId.toString(),
            token = token
        )
    }


}
