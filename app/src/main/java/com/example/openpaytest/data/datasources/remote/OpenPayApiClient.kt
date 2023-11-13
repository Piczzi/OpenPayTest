package com.example.openpaytest.data.datasources.remote

import com.example.openpaytest.BuildConfig
import com.example.openpaytest.data.model.remote.DetailPersonDTO
import com.example.openpaytest.data.model.remote.MoviesDTO
import com.example.openpaytest.data.model.remote.PopularPersonsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenPayApiClient {

    @GET("person/popular?language=es-US&page=1?sort_by=popularity.desc")
    suspend fun getPopularPersons(
        @Query("api_key") apiKey: String? = BuildConfig.MOVIE_API_KEY,
    ): Response<PopularPersonsDTO>?

    @GET("person/{person_id}")
    suspend fun getDetailPerson(
        @Path("person_id") personId: String,
        @Query("api_key") apiKey: String? = BuildConfig.MOVIE_API_KEY,
    ): Response<DetailPersonDTO>?

    @GET("movie/popular?language=es-US&page=1")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String? = BuildConfig.MOVIE_API_KEY,
    ): Response<MoviesDTO>?

    @GET("movie/top_rated?language=es-US&page=1")
    suspend fun getBestRatedMovies(
        @Query("api_key") apiKey: String? = BuildConfig.MOVIE_API_KEY,
    ): Response<MoviesDTO>?

    @GET("movie/upcoming?language=es-US&page=1")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String? = BuildConfig.MOVIE_API_KEY,
    ): Response<MoviesDTO>?

}