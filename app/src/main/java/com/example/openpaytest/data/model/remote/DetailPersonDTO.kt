package com.example.openpaytest.data.model.remote

import com.google.gson.annotations.SerializedName

data class DetailPersonDTO(
    val adult: Boolean,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String? = "No disponible",
    val birthday: String? = "No disponible",
    val deathday: String? = "No disponible o aún vivo",
    val gender: Long,
    val homepage: Any?,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String? = "No disponible",
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = "No disponible",
    val popularity: Double? = 0.0,
    @SerializedName("profile_path")
    val profilePath: String? = "No disponible",
)
