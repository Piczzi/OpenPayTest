package com.example.openpaytest.data.repositories

import com.example.openpaytest.data.datasources.remote.OpenPayApiClient
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.data.model.remote.MoviesDTO
import com.example.openpaytest.utils.MethodsHandler
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: OpenPayApiClient) {

    suspend fun readPopularMovies(): List<UserMovies>? {
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
                }.orEmpty()

                return movieList
            }
        }

        return null
    }

    suspend fun readBestRatedMovies(): List<UserMovies>? {
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
                }.orEmpty()

                return movieList
            }
        }

        return null
    }

    suspend fun readUpcomingMovies(): List<UserMovies>? {
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
                }.orEmpty()

                return movieList
            }
        }

        return null
    }

}