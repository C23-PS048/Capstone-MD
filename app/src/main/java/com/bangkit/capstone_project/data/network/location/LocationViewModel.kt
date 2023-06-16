package com.bangkit.capstone_project.data.network.location

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.capstone_project.domain.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {

    var currentLocation by mutableStateOf<Location?>(getDefaultLocation())
        private set

    fun getCurrentLocation() {
        viewModelScope.launch {
            currentLocation = locationTracker.getCurrentLocation()
        }
    }

    private fun getDefaultLocation(): Location {
        val defaultLatitude = 	-6.9
        val defaultLongitude =110.4
        val location = Location("")
        location.latitude = defaultLatitude
        location.longitude = defaultLongitude
        return location
    }
}
