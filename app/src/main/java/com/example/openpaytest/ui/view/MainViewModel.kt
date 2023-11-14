package com.example.openpaytest.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _isLocationPermissionGranted = MutableLiveData<Boolean>(false)
    val isLocationPermissionGranted: LiveData<Boolean> = _isLocationPermissionGranted

    private val _isNecessaryCheckLocationPermission = MutableLiveData<Boolean>(false)
    val isNecessaryCheckLocationPermission: LiveData<Boolean> = _isNecessaryCheckLocationPermission

    fun updatePermissionGranted(isGranted: Boolean) {
        _isLocationPermissionGranted.postValue(isGranted)
    }

    fun checkIfIsNecessaryCheckLocationPermission() {
        _isNecessaryCheckLocationPermission.postValue(true)
    }

}