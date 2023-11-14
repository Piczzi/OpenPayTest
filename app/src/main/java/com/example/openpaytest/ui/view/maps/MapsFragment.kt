package com.example.openpaytest.ui.view.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.openpaytest.R
import com.example.openpaytest.databinding.FragmentMapsBinding
import com.example.openpaytest.services.LocationUpdateService
import com.example.openpaytest.ui.view.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    private lateinit var serviceIntent: Intent

    @Inject
    lateinit var firestore: FirebaseFirestore

    companion object {
        const val REQ_CODE_LOCATION: Int = 1997
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun startService() {
        ContextCompat.startForegroundService(requireContext(), serviceIntent)
    }

    private fun stopService() {
        requireContext().stopService(serviceIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceIntent = Intent(requireActivity(), LocationUpdateService::class.java)
        startService()
        createFragment()

        binding.btnOtherPin.setOnClickListener { moveToNextMarker() }
    }

    private val markersList = mutableListOf<MarkerOptions>()

    private fun readCollections() {
        firestore.collection("Locations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val markOption = MarkerOptions().position(
                        LatLng(
                            document.getDouble("latitude") ?: 0.0,
                            document.getDouble("longitude") ?: 0.0
                        )
                    ).title(document.getString("date"))
                    markersList.add(markOption)
                }
                createMarkers()
            }
            .addOnFailureListener { exception ->
                Log.e("SMM_DEBUG", "Error al leer la db: ${exception.message}")
            }
    }

    private var currentMarkerIndex = 0

    private fun moveToNextMarker() {
        if (markersList.isNotEmpty()) {
            val nextMarker = markersList[currentMarkerIndex]
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(nextMarker.position, 18f))
            currentMarkerIndex = (currentMarkerIndex + 1) % markersList.size
        }
    }

    private fun createFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        readCollections()
        enableLocation()
    }

    @SuppressLint("MissingPermission")
    private fun createMarkers() {
        markersList.forEach { marker ->
            map.addMarker(marker)
        }
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocaltionPermission()
        }
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            mainViewModel.updatePermissionGranted(true)
        } else {
            requestLocaltionPermission()
        }
    }

    private fun requestLocaltionPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                requireContext(),
                "Activa los permisos en ajustes",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQ_CODE_LOCATION
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQ_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainViewModel.updatePermissionGranted(true)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Activa los permisos en ajustes!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                requireContext(),
                "Activa los permisos en ajustes!!!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

}