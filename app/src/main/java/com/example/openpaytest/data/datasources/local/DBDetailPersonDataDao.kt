package com.example.openpaytest.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.openpaytest.data.model.local.DBDetailPersonViewData

@Dao
interface DBDetailPersonDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(viewData: DBDetailPersonViewData)

    @Query("SELECT * FROM db_detail_person_view_data ORDER BY id LIMIT 1")
    suspend fun read(): DBDetailPersonViewData?

    @Query("SELECT * FROM db_detail_person_view_data")
    suspend fun readAll(): List<DBDetailPersonViewData>?

    @Query("DELETE FROM db_detail_person_view_data")
    suspend fun deleteAll()

    @Transaction
    suspend fun insert(viewData: DBDetailPersonViewData): DBDetailPersonViewData {
        if (readAll().isNullOrEmpty().not()) deleteAll()
        createOrUpdate(viewData)
        return read() ?: viewData
    }
}