package com.dark.stalker

import android.content.Context
import android.content.pm.PackageManager

object AppUtils {
    fun getInstalledApps(context: Context): String {
        val packages = context.packageManager.getInstalledPackages(0)
        val appNames = packages.map { it.applicationInfo.loadLabel(context.packageManager).toString() }
        return appNames.joinToString(", ")
    }
}
