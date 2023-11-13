package com.example.openpaytest.ui.view.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openpaytest.data.model.local.DetailPersonViewData
import com.example.openpaytest.domain.GetPersonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val personsUseCase: GetPersonsUseCase,
): ViewModel() {

    private val _popularPerson = MutableLiveData<DetailPersonViewData>()
    val popularPerson: LiveData<DetailPersonViewData> = _popularPerson

    fun getMostPopularPerson() = viewModelScope.launch(Dispatchers.IO) {
        personsUseCase()?.let { _popularPerson.postValue(it) } ?: run { showTemporallyError() }
    }

    private fun showTemporallyError() {
        Log.i("SMM_DEBUG", "Algo salió nuloooo")
    }

}