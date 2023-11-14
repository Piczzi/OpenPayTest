package com.example.openpaytest.ui.view.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.data.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
): ViewModel() {

    private val _popularMovies = MutableLiveData<List<UserMovies>>()
    val popularMovies: LiveData<List<UserMovies>> = _popularMovies

    private val _bestRatedMovies = MutableLiveData<List<UserMovies>>()
    val bestRatedMovies: LiveData<List<UserMovies>> = _bestRatedMovies

    private val _upcomingMovies = MutableLiveData<List<UserMovies>>()
    val upcomingMovies: LiveData<List<UserMovies>> = _upcomingMovies

    private val _showError = MutableLiveData<String>("")
    val showError: LiveData<String> = _showError

    fun getMostPopularMovies(weHaveInternetConection: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.readPopularMovies(weHaveInternetConection)?.let { _popularMovies.postValue(it) } ?: run { showTemporallyError() }
    }

    fun getBestRatedMovies(weHaveInternetConection: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.readBestRatedMovies(weHaveInternetConection)?.let { _bestRatedMovies.postValue(it) } ?: run { showTemporallyError() }
    }

    fun getUpcomingMovies(weHaveInternetConection: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.readUpcomingMovies(weHaveInternetConection)?.let { _upcomingMovies.postValue(it) } ?: run { showTemporallyError() }
    }

    private fun showTemporallyError() {
        _showError.postValue("Algo salió mal, revisa tu conexión a internet e intenta más tarde.")
    }

}