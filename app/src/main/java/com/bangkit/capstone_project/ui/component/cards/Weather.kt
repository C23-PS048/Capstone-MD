package com.bangkit.capstone_project.ui.component.cards

import android.content.Context
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.helper.getAddressName
import com.bangkit.capstone_project.network.weather.WeatherViewModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.LoadingAnimation
import com.bangkit.capstone_project.ui.theme.GreenLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherCards(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    currentLocation: Location?,
    context:Context
) {
    var address: MutableState<String?> = remember { mutableStateOf("") }
    Card(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), elevation =CardDefaults.cardElevation(defaultElevation = 4.dp) ) {
    weatherViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->

        when (uiState) {
            is UiState.Loading -> {
                Row(modifier.fillMaxWidth().padding(32.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    LoadingAnimation()
                }
                currentLocation?.latitude?.let {lat->
                    currentLocation.longitude.let { lon ->
                        weatherViewModel.getWeather(lat,
                            lon
                        )
                        address = getAddressName(context,lat,lon)
                    }
                }
            }

            is UiState.Success -> {

                Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AsyncImage(model = "https://openweathermap.org/img/wn/${uiState.data?.weather?.get(0)?.icon}@2x.png", contentDescription = null,
                        modifier
                            .size(100.dp)
                            .background(
                                GreenLight, RoundedCornerShape(15)
                            ))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = modifier.padding(4.dp)) {
                        Text(text = "${uiState.data?.weather?.get(0)?.main} | ${uiState.data?.main?.temp} C", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                        Text(text = "${address.value}",style = MaterialTheme.typography.titleMedium, color = Color.Black)
                        Text(text = "This Weather is Perfect for Your Plants",style = MaterialTheme.typography.bodySmall, color = Color.Black)
                    }
                }
            }

            is UiState.Error -> {
                // Handle the error state
            }


        }

    }



    }
}

