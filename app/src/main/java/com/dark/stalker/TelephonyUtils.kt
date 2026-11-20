package com.dark.stalker

import android.content.Context
import android.telephony.TelephonyManager

object TelephonyUtils {
    fun getImei(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return try {
            tm.deviceId ?: "Unknown IMEI"
        } catch (_: SecurityException) {
            "Unknown IMEI"
        }
    }

    fun getPhoneNumber(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return try {
            tm.line1Number ?: "Unknown Number"
        } catch (_: SecurityException) {
            "Unknown Number"
        }
    }
}
