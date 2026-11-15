package com.example.kopinusantara

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/** Cek koneksi internet dengan aman (tidak crash walau terjadi error). */
fun isOnline(context: Context): Boolean {
    return try {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } catch (e: Exception) {
        false
    }
}
