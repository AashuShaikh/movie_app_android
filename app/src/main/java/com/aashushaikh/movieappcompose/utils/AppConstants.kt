package com.aashushaikh.movieappcompose.utils

import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object AppConstants {

    const val BASE_URL = "http://10.0.2.2:8080/"

    const val AUTH_REFERENCES = "auth_datastore"

    fun yearFormatter(dateString: String?): String{
        dateString?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val dateTime = LocalDateTime.parse(dateString, formatter)
            val year = dateTime.year
            return year.toString()
        }?: return ""
    }

    fun isJwtExpired(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return true

            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)
            val exp = json.getLong("exp") * 1000 // exp is in seconds
            val currentTime = System.currentTimeMillis()

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()

            Log.d("JWT", "Token expiry time: ${sdf.format(Date(exp))}")
            Log.d("JWT", "Current time: ${sdf.format(Date(currentTime))}")

            currentTime >= exp
        } catch (e: Exception) {
            Log.e("JWT", "Error parsing token", e)
            true // Treat errors as expired
        }
    }



}