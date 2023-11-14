package com.example.openpaytest.data.repositories

import android.util.Log
import com.example.openpaytest.data.datasources.local.DBMostRatedMoviesDataDao
import com.example.openpaytest.data.datasources.local.DBPopularMoviesDataDao
import com.example.openpaytest.data.datasources.local.DBUpcomingMoviesDataDao
import com.example.openpaytest.data.datasources.remote.OpenPayApiClient
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.utils.MethodsHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: OpenPayApiClient,
    private val popularMoviesDao: DBPopularMoviesDataDao,
    private val mostRatedMoviesDao: DBMostRatedMoviesDataDao,
    private val upcomingMoviesDao: DBUpcomingMoviesDataDao,
) {

    suspend fun readPopularMovies(weHaveInternetConnection: Boolean): List<UserMovies>? {
        if (weHaveInternetConnection) { // Consume api
            val response = api.getPopularMovies()
            if (response?.isSuccessful == true) {
                response.body()?.let {  responseBody ->
                    val movieList = responseBody.results.map { movie ->
                        UserMovies(
                            id = movie.id.toString(),
                            title = MethodsHandler.validateEmptyField(movie.title),
                            review = MethodsHandler.validateEmptyField(movie.overview),
                            posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                            releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                            score = movie.voteAverage,
                        )
                    }
                    popularMoviesDao.insert(movieList)
                    return movieList
                }
            }
        } else { // Retrive data from db
            try {
                val popularMovies = popularMoviesDao.readAll()?.get(0)
                val moviesListType = object : TypeToken<List<UserMovies>>() {}.type
                val moviesList: List<UserMovies>? = Gson().fromJson(popularMovies?.movies ?: "", moviesListType)

                if (moviesList.isNullOrEmpty()) return null
                return moviesList.map { movie ->
                    UserMovies(
                        id = movie.id.toString(),
                        title = MethodsHandler.validateEmptyField(movie.title),
                        review = MethodsHandler.validateEmptyField(movie.review),
                        posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                        releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                        score = movie.score,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SMM_DEBUG", "Error al obtener peliculas populares: ${e.message}")
                return null
            }
        }

        return null
    }

    suspend fun readBestRatedMovies(weHaveInternetConnection: Boolean): List<UserMovies>? {
        if (weHaveInternetConnection) {
            val response = api.getBestRatedMovies()
            if (response?.isSuccessful == true) {
                response.body()?.let {  responseBody ->
                    val movieList = responseBody.results.map { movie ->
                        UserMovies(
                            id = movie.id.toString(),
                            title = MethodsHandler.validateEmptyField(movie.title),
                            review = MethodsHandler.validateEmptyField(movie.overview),
                            posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                            releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                            score = movie.voteAverage,
                        )
                    }
                    mostRatedMoviesDao.insert(movieList)
                    return movieList
                }
            }
        } else {
            try {
                val mostRatedMovies = mostRatedMoviesDao.readAll()?.get(0)
                val moviesListType = object : TypeToken<List<UserMovies>>() {}.type
                val moviesList: List<UserMovies>? = Gson().fromJson(mostRatedMovies?.movies ?: "", moviesListType)

                if (moviesList.isNullOrEmpty()) return null
                return moviesList.map { movie ->
                    UserMovies(
                        id = movie.id.toString(),
                        title = MethodsHandler.validateEmptyField(movie.title),
                        review = MethodsHandler.validateEmptyField(movie.review),
                        posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                        releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                        score = movie.score,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SMM_DEBUG", "Error al obtener peliculas populares: ${e.message}")
                return null
            }
        }

        return null
    }

    suspend fun readUpcomingMovies(weHaveInternetConnection: Boolean): List<UserMovies>? {
        if (weHaveInternetConnection) {
            val response = api.getUpcomingMovies()
            if (response?.isSuccessful == true) {
                response.body()?.let {  responseBody ->
                    val movieList = responseBody.results.map { movie ->
                        UserMovies(
                            id = movie.id.toString(),
                            title = MethodsHandler.validateEmptyField(movie.title),
                            review = MethodsHandler.validateEmptyField(movie.overview),
                            posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                            releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                            score = movie.voteAverage,
                        )
                    }
                    upcomingMoviesDao.insert(movieList)
                    return movieList
                }
            }
        } else {
            try {
                val upcomingMovies = upcomingMoviesDao.readAll()?.get(0)
                val moviesListType = object : TypeToken<List<UserMovies>>() {}.type
                val moviesList: List<UserMovies>? = Gson().fromJson(upcomingMovies?.movies ?: "", moviesListType)

                if (moviesList.isNullOrEmpty()) return null
                return moviesList.map { movie ->
                    UserMovies(
                        id = movie.id.toString(),
                        title = MethodsHandler.validateEmptyField(movie.title),
                        review = MethodsHandler.validateEmptyField(movie.review),
                        posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                        releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                        score = movie.score,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SMM_DEBUG", "Error al obtener peliculas populares: ${e.message}")
                return null
            }
        }

        return null
    }

}