package com.example.openpaytest.data.model.remote

import com.google.gson.annotations.SerializedName

data class PopularPersonsDTO(
    val page: Long,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("total_results")
    val totalResults: Long,
)

data class Result(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("known_for")
    val knownFor: List<KnownFor>,
)

data class KnownFor(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Long,
    val title: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("genre_ids")
    val genreIds: List<Long>,
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
)
