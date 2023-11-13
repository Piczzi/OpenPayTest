package com.example.openpaytest.data.model.remote

import com.google.gson.annotations.SerializedName

data class DetailPersonDTO(
    val adult: Boolean,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: String,
    val gender: Long,
    val homepage: Any?,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
)
