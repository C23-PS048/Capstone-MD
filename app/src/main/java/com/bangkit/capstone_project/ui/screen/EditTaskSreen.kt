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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.data.network.userplant.UserPlant
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
import com.bangkit.capstone_project.ui.theme.RedDark
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun EditTaskScreen(
    id: Int,
    onBack: () -> Unit,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier,
    plantViewModel: PlantViewModel,
    userPlantViewModel: UserPlantViewModel,
    prefViewModel: PreferenceViewModel,
    showToast: (String) -> Unit
) {
    val session by prefViewModel.getLoginSession().collectAsState(initial = null)
    val plantId = mutableIntStateOf(id)
    val planData: MutableState<PlantResult?> = remember {
        mutableStateOf(null)
    }
    val isDelete: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val idPlant = remember {
        mutableStateOf(0)
    }
    DisposableEffect(Unit) {
        onDispose {
            userPlantViewModel.resetData()

        }
    }
    userPlantViewModel.responseState.collectAsState(initial = null).value.let { responseState ->
        when (responseState) {
            is UiState.Loading -> {


                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                if(isDelete.value){
                    showToast("Data Terhapus")
                    userPlantViewModel.resetResponseState()
                    navigateHome()
                }else{
                    showToast("Data Berhasil Di update")
                    userPlantViewModel.resetResponseState()
                    onBack()
                }

            }

            is UiState.Error -> {
                showToast(responseState.errorMessage)
            }

            else -> {}
        }

    }

    userPlantViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                session?.token?.let { userPlantViewModel.getPlant(plantId.value, it) }
            }

            is UiState.Success -> {
                val data = uiState.data?.userPlant
                if (data != null) {
                    idPlant.value = data.plantId
                }

                planData.value?.let { plant ->
                    EditTaskContent(
                        task = data,
                        session = session,
                        onBack = onBack,
                        isDelete = isDelete,
                        onDeletChange = {isDelete.value = it},
                        showToast = showToast,
                        navigateHome = navigateHome,
                        plant = plant,
                        userPlantViewModel = userPlantViewModel,

                        )
                }


            }

            is UiState.Error -> {
                showToast(uiState.errorMessage)
            }

            else -> {}
        }
    }
    plantViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                plantViewModel.getAll()


            }

            is UiState.Success -> {
                uiState.data?.plantList?.forEach {
                    if (it.id == idPlant.value) {
                        planData.value = it
                    }
                }


            }

            is UiState.Error -> {
                showToast(uiState.errorMessage)
            }

        }
    }

}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskContent(
    task: UserPlant?,
    onBack: () -> Unit,
    navigateHome: () -> Unit,
    modifier: Modifier = Modifier,
    plant: PlantResult,
    session: UserModel?,
    userPlantViewModel: UserPlantViewModel,
    showToast: (String) -> Unit,
    isDelete: MutableState<Boolean>,
    onDeletChange: (Boolean) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }
    val openWeatherDate = remember { mutableStateOf(false) }
    val openWeatherRepeat = remember { mutableStateOf(false) }
    val radioOptions = listOf(
        Frequency("Setiap Hari", 1),
        Frequency("Dua Hari Sekali", 2),
        Frequency("Setiap Tiga Hari", 3),
        Frequency("Setiap Empat Hari", 4),
        Frequency("Lima Hari Sekali", 5),
        Frequency("Enam Hari Sekali", 6),

        )

    val options = task?.frequency?.toInt()

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[options!! - 1]) }
    val textLocation = mutableStateOf(task?.location)
    val dateWeatherState =
        rememberDatePickerState(initialSelectedDateMillis = task?.lastScheduledDate?.toLong())
    Log.d("TAG", "EditTaskContent:${dateWeatherState.selectedDateMillis} ")
    CapstoneProjectTheme() {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
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

                                    AsyncImage(
                                        model = plant.image[0],
                                        contentDescription = "Plant Hint",
                                        contentScale = ContentScale.Crop,
                                        modifier = modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }





                        dateWeatherState.selectedDateMillis?.let {
                            convertMillisToDateString(it)
                        }?.let { date ->
                            ScheduleInput(
                                value = openWeatherDate.value,
                                onValueChange = { openWeatherDate.value = it },
                                selectDialog = openWeatherRepeat.value,
                                isSelectOpen = { openWeatherRepeat.value = it },
                                Date = date,
                                Selected = selectedOption.label,
                                modifier = modifier.background(Color.White)
                            )
                        }

                        textLocation.value?.let { it ->
                            OutlinedTextField(
                                value = it,
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
                        }
                        Button(
                            onClick = {
                                onDeletChange(false)
                                if (task != null) {
                                    if (session != null) {
                                        textLocation.value?.let { loc ->
                                            dateWeatherState.selectedDateMillis?.let { lastDate ->
                                                task.startDate?.let { date ->
                                                    session.token?.let { token ->
                                                        updateData(
                                                            task = task,
                                                            location = loc,
                                                            frequency = selectedOption.frequency,
                                                            lastDate = lastDate,
                                                            startDate = date.toLong(),
                                                            userPlantViewModel = userPlantViewModel,
                                                            token = token
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                /* onBack()*/
                            },
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(text = "Update")
                        }
                        Button(
                            onClick = {

                                openDialog.value = !openDialog.value
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = RedDark),
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(text = "Delete")
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
                                            onClick = null
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

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {

                    openDialog.value = false
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = modifier.size(32.dp)
                    )
                },
                title = {
                    Text(text = "Hapus?")
                },
                text = {
                    Text(
                        "Anda yakin ingin menghapus Tanamn ini?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
onDeletChange(true)
                            if (task != null) {
                                session?.token?.let {
                                    deleteData(
                                        task = task,
                                        userPlantViewModel = userPlantViewModel, token = it
                                    )
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
}


fun updateData(
    task: UserPlant?,
    frequency: Int,
    lastDate: Long,
    startDate: Long,
    location: String,
    token: String,
    userPlantViewModel: UserPlantViewModel,
) {


    val (lastScheduledDate, nextScheduledDate) = calculateScheduleDates(
        lastDate,
        frequency
    )



    userPlantViewModel.updateTask(
        location = location,
        disease = "healthy",
        startDate = startDate.toString(),
        lastScheduledDate = lastScheduledDate.toString(),
        nextScheduledDate = nextScheduledDate.toString(),
        frequency = frequency.toString(),
        plantId = task?.plantId.toString(),
        userId = task?.userId.toString(),
        token = token,
        id = task?.id.toString()
    )
}

fun deleteData(task: UserPlant, userPlantViewModel: UserPlantViewModel, token: String) {
    task.id?.let { userPlantViewModel.deletePlant(it, token) }
}

