package com.dark.stalker

import android.content.Context
import android.telephony.TelephonyManager

object TelephonyUtils {
    fun getImei(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (tm.deviceId != null) tm.deviceId else "Unknown IMEI"
    }

    fun getPhoneNumber(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.lineNumber ?: "Unknown Number"
    }
}
