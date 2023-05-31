package com.bangkit.capstone_project.helper

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.io.IOException
import java.util.Locale

fun getAddressName(context:Context,lat: Double, lon: Double): MutableState<String?> {
    var addressName: MutableState<String?> = mutableStateOf(null)
    val geocoder = Geocoder(context, Locale.US)
    try {
        val list = geocoder.getFromLocation(lat, lon, 1)
        if (list != null && list.size != 0) {
            addressName = mutableStateOf("${list[0].subAdminArea}, ${list[0].countryName}")

        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return addressName
}