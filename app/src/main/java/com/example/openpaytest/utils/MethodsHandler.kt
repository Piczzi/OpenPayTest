package com.example.openpaytest.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

object MethodsHandler {

    fun validateEmptyField(value: String?): String {
        return if (value.isNullOrBlank()) Constants.EMPTY_LABEL else value
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.fromMillisToDateString(format: String = "yyyy-MM-dd"): String {
        val formatter = SimpleDateFormat(format)
        return formatter.format(this)
    }

    fun String.randomUUID(): String {
        return this + UUID.randomUUID().toString().lowercase(Locale.getDefault()) + ".jpg"
    }

}