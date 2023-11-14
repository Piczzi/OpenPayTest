package com.example.openpaytest.data.repositories

import android.util.Log
import com.example.openpaytest.data.datasources.local.DBDetailPersonDataDao
import com.example.openpaytest.data.datasources.remote.OpenPayApiClient
import com.example.openpaytest.data.model.local.DBDetailPersonViewData
import com.example.openpaytest.data.model.local.DetailPersonViewData
import com.example.openpaytest.data.model.remote.DetailPersonDTO
import com.example.openpaytest.data.model.remote.PopularPersonsDTO
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val api: OpenPayApiClient,
    private val detailPersonDataDao: DBDetailPersonDataDao,
) {

    suspend fun read(): Response<PopularPersonsDTO>? = api.getPopularPersons()

    suspend fun eraseFieldsFromDb() = detailPersonDataDao.deleteAll()

    suspend fun readFromDb(): List<DBDetailPersonViewData>? {
        return try {
            detailPersonDataDao.readAll()
        } catch (_: Error) {
            null
        }
    }

    suspend fun readDetailPerson(personId: String): Response<DetailPersonDTO>? = api.getDetailPerson(personId)

    suspend fun saveInDb(person: DetailPersonViewData): Boolean {
        return try {
            detailPersonDataDao.insert(
                DBDetailPersonViewData(
                    id = person.id ?: "1",
                    name = person.name,
                    biography = person.biography,
                    birthday = person.birthday,
                    deathday = person.deathday,
                    birthPlace = person.birthPlace,
                    popularity = person.popularity,
                    profileImagePath = person.profileImagePath,
                    movies = Gson().toJson(person.movies),
                )
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("SMM_DEBUG", "Error al guardar en la db. Message: ${e.message}")
            false
        }
    }

}