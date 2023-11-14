package com.example.openpaytest.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_detail_person_view_data")
data class DBDetailPersonViewData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String? = "No disponible",
    val biography: String? = "No disponible",
    val birthday: String? = "No disponible",
    val deathday: String? = "No disponible (Probablemente vivo)",
    val birthPlace: String? = "No disponible",
    val popularity: Double? = 0.0,
    val profileImagePath: String? = "",
    val movies: String? = "", //Json of -> List<UserMovies>?
)
