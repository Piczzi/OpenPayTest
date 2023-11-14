package com.example.openpaytest.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_upcoming_movies_view_data")
data class DBUpcomingMoviesViewData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val movies: String? = "", //Json of -> List<UserMovies>?
)
