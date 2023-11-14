package com.example.openpaytest.services

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.openpaytest.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class LocationUpdateService : Service() {

    private val NOTIFICATION_CHANNEL_ID = "com.example.openpaytest"

    private lateinit var currentLatLng: LatLng
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocation: FusedLocationProviderClient

    @Inject
    lateinit var firestore: FirebaseFirestore

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0 ?: return
            for (location in p0.locations){
                currentLatLng = LatLng(p0.lastLocation?.latitude ?: 0.0, p0.lastLocation?.longitude ?: 0.0)
                updateLocationInFirestore(currentLatLng.latitude, currentLatLng.longitude)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        startLocation()
    }

    override fun onDestroy() {
        if (locationCallback != null && fusedLocation != null) {
            fusedLocation.removeLocationUpdates(locationCallback)
        }
        super.onDestroy()
    }

    private fun startLocation() {
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest()
        locationRequest.interval = 300000 // 5 minutes
        locationRequest.fastestInterval = 300000 // 5 minutes
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.smallestDisplacement = 0.1f

        if (ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    @SuppressLint("HardwareIds")
    private fun updateLocationInFirestore(latitude: Double, longitude: Double) {
        val locationData: MutableMap<String, Any> = mutableMapOf()
        locationData["latitude"] = latitude
        locationData["longitude"] = longitude
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        locationData["date"] = sdf.format(Date())

        firestore.collection("Locations").document().set(locationData)
            .addOnSuccessListener {
                Log.i("SMM_DEBUG", "Localización guardada con éxito.")
            }
            .addOnFailureListener {
                Log.i("SMM_DEBUG", "Error al guardar ubi: ${it.message}")
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_location)
            .setContentTitle("Actualización de ubicación en curso")
            .setContentText("La ubicación se está actualizando cada 5 minutos.")
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyForegroundService()
        } else {
            startForeground(1997, notification)
        }
        return START_STICKY
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startMyForegroundService() {
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "MyForegroundService", NotificationManager.IMPORTANCE_HIGH)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager: NotificationManager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(channel)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification: Notification = builder
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_location)
            .setContentTitle("Actualización de ubicación en curso")
            .setContentText("La ubicación se está actualizando cada 5 minutos.")
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(1997, notification)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}