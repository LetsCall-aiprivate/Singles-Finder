package com.dark.stalker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object HttpUtils {
    suspend fun postJson(url: String, json: String) {
        withContext(Dispatchers.IO) {
            try {
                val u = URL(url)
                val connection = u.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                connection.doOutput = true
                OutputStreamWriter(connection.outputStream).use { it.write(json) }
                connection.disconnect()
            } catch (e: Exception) {
                // Silent fail or log
            }
        }
    }
}
