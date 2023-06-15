package com.bangkit.capstone_project.ui.screen

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.data.network.userplant.UserPlantItem
import com.bangkit.capstone_project.data.network.userplant.UserPlantViewModel
import com.bangkit.capstone_project.data.network.weather.WeatherViewModel
import com.bangkit.capstone_project.helper.getAddressName
import com.bangkit.capstone_project.helper.getCurrentDate
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.InfoScreen
import com.bangkit.capstone_project.ui.component.LoadingAnimation
import com.bangkit.capstone_project.ui.component.cards.OwnPlantCard
import com.bangkit.capstone_project.ui.component.cards.WeatherCards
import com.bangkit.capstone_project.ui.theme.BlackLight
import com.bangkit.capstone_project.ui.theme.GreenMed
import com.bangkit.capstone_project.ui.theme.Ivory
import com.bangkit.capstone_project.viewmodel.Injection
import com.bangkit.capstone_project.viewmodel.ViewModelFactory
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,

    plantViewModel: PlantViewModel,
    userPlantViewModel: UserPlantViewModel,
    weatherViewModel: WeatherViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    currentLocation: Location?,
    navigatetoOwned: (Int) -> Unit,
    token: String,


    id: String,

    prefViewModel: PreferenceViewModel,
    navController: NavHostController
) {

    var list = mutableListOf<PlantResult>()
    currentLocation?.latitude?.let { lat ->
        currentLocation.longitude.let { long ->
            weatherViewModel.getWeather(
                lat,
                long
            )
        }
    }
    plantViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {

                plantViewModel.getAll()


            }

            is UiState.Success -> {

                val data = uiState.data?.plantList
                Log.d("TAG", "ListScreen: $data")
                if (data != null) {

                    list = data.toMutableList()
                }


            }

            is UiState.Error -> {}

        }
    }

    val session by prefViewModel.getLoginSession().collectAsState(initial = null)

    userPlantViewModel.userPlant.collectAsState(initial = UiState.Loading).value.let { uiState ->

        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                session?.apply {
                    userPlantViewModel.getUserPlant(id, token)

                }
            }

            is UiState.Success -> {

                HomeContent(
                    navController = navController,
                    weatherViewModel = weatherViewModel,
                    currentLocation = currentLocation,
                    listTask = uiState.data?.userPlant,
                    navigatetoOwned = navigatetoOwned,
                    username = session?.name,
                    context = LocalContext.current,
                    plantList = list
                )


            }

            is UiState.Error -> {}
        }

    }


}

@Composable
fun HomeContent(
    username: String?,
    modifier: Modifier = Modifier,
    currentLocation: Location?,
    listTask: List<UserPlantItem?>?,
    plantList: MutableList<PlantResult>,
    weatherViewModel: WeatherViewModel,
    navigatetoOwned: (Int) -> Unit,
    navController: NavHostController,
    context: Context
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Ivory)
            .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = getCurrentDate(),
                    style = MaterialTheme.typography.titleMedium,
                    color = BlackLight
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Hello,", fontSize = 20.sp)
                    if (username != null) {
                        Text(text = username, fontSize = 20.sp, color = GreenMed)
                    }
                }
            }
            Icon(painter = painterResource(id = R.drawable.bell), contentDescription = null)
        }


        var address:String = "Semarang"
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            weatherViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->

                when (uiState) {
                    is UiState.Loading -> {
                        Card(
                            modifier = modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LoadingAnimation()
                            }
                        }
                        val lat = currentLocation?.latitude ?: -6.966667
                        val lon = currentLocation?.longitude ?: 110.416664

                        weatherViewModel.getWeather(
                            lat,
                            lon
                        )
                        address = getAddressName(context, lat, lon)
                    }

                    is UiState.Success -> {



                            WeatherCards(
                                address = address,
                                icon = uiState.data?.weather?.get(0)?.icon,
                                info = "${uiState.data?.weather?.get(0)?.main} | ${uiState.data?.main?.temp} C",
                                weatherViewModel = weatherViewModel,
                                currentLocation = currentLocation,
                                context = LocalContext.current
                            )

                    }

                    is UiState.Error -> {
                        // Handle the error state
                    }


                }

            }


        }











        Text(text = "Your Plant", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        if (listTask != null) {
            if (listTask.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {

                    items(listTask) { item ->

                        if (item != null) {
                            item.location?.let { loc ->
                                item.id?.let { id ->
                                    plantList.forEach { plantResult ->
                                        if (plantResult.id == item.plantId) {
                                            OwnPlantCard(
                                                location = loc,
                                                plantName = plantResult.plantName,
                                                plantImage = plantResult.image,
                                                plantScientifi = plantResult.scientificName,
                                                navigatetoOwned = {
                                                    navController.navigate(
                                                        Screen.OwnedPlant.createRoute(
                                                            id
                                                        )
                                                    )
                                                },
                                                id = id
                                            )
                                            Log.d("TAG", "HomeContent: id = $id")
                                        }
                                    }

                                }
                            }
                        }
                    }


                }
            } else {

                InfoScreen()

            }
        }

    }
}
