package com.example.openpaytest.data.datasources.local

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.openpaytest.data.model.local.DBPopularMoviesViewData
import com.example.openpaytest.data.model.local.DBUpcomingMoviesViewData
import com.example.openpaytest.data.model.local.UserMovies
import com.google.gson.Gson

@Dao
interface DBUpcomingMoviesDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(viewData: DBUpcomingMoviesViewData)

    @Query("SELECT * FROM db_upcoming_movies_view_data ORDER BY id LIMIT 1")
    suspend fun read(): DBUpcomingMoviesViewData?

    @Query("SELECT * FROM db_upcoming_movies_view_data")
    suspend fun readAll(): List<DBUpcomingMoviesViewData>?

    @Query("DELETE FROM db_upcoming_movies_view_data")
    suspend fun deleteAll()

    @Transaction
    suspend fun insert(movieList: List<UserMovies>): Boolean {
        return try {
            if (readAll().isNullOrEmpty().not()) deleteAll()
            createOrUpdate(DBUpcomingMoviesViewData("1", Gson().toJson(movieList)))
            true
        } catch (e: Error) {
            e.printStackTrace()
            Log.e("SMM_DEBUG", "Error al guardar peliculas proximas en la db: ${e.message}")
            false
        }
    }
}