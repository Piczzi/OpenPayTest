package com.example.openpaytest.data.repositories

import com.example.openpaytest.data.datasources.remote.OpenPayApiClient
import com.example.openpaytest.data.model.remote.DetailPersonDTO
import com.example.openpaytest.data.model.remote.PopularPersonsDTO
import retrofit2.Response
import javax.inject.Inject

class PersonRepository @Inject constructor(private val api: OpenPayApiClient) {

    suspend fun read(): Response<PopularPersonsDTO>? = api.getPopularPersons()

    suspend fun readDetailPerson(personId: String): Response<DetailPersonDTO>? = api.getDetailPerson(personId)

}