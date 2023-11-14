package com.example.openpaytest.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.openpaytest.data.model.local.DBDetailPersonViewData
import com.example.openpaytest.data.model.local.DBMostRatedMoviesViewData
import com.example.openpaytest.data.model.local.DBPopularMoviesViewData
import com.example.openpaytest.data.model.local.DBUpcomingMoviesViewData

@Database(
    entities = [DBDetailPersonViewData::class, DBPopularMoviesViewData::class, DBMostRatedMoviesViewData::class, DBUpcomingMoviesViewData::class],
    version = 1,
    exportSchema = false
)
abstract class OpenPayDatabase : RoomDatabase() {
    abstract fun personDetailDataDao(): DBDetailPersonDataDao
    abstract fun popularMoviesDataDao(): DBPopularMoviesDataDao
    abstract fun mostRatedMoviesDataDao(): DBMostRatedMoviesDataDao
    abstract fun upcomingMoviesDataDao(): DBUpcomingMoviesDataDao
}
