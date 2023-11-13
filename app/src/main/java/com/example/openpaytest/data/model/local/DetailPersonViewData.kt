package com.example.openpaytest.data.model.local

data class DetailPersonViewData(
    val id: String? = "",
    val name: String? = "No disponible",
    val biography: String? = "No disponible",
    val birthday: String? = "No disponible",
    val deathday: String? = "No disponible (Probablemente vivo)",
    val birthPlace: String? = "No disponible",
    val popularity: Double? = 0.0,
    val profileImagePath: String? = "",
    val movies: List<UserMovies>?,
)

data class UserMovies(
    val id: String? = "",
    val title: String? = "No disponible",
    val review: String? = "No disponible",
    val posterPath: String? = "No disponible",
    val releaseDate: String? = "No disponible",
    val score: Double? = 0.0
)
