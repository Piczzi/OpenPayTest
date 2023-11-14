package com.example.openpaytest.domain

import android.util.Log
import com.example.openpaytest.data.model.local.DetailPersonViewData
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.data.repositories.PersonRepository
import com.example.openpaytest.utils.MethodsHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val personRepository: PersonRepository,
) {

    suspend operator fun invoke(weHaveInternetConection: Boolean): DetailPersonViewData? {

        if (weHaveInternetConection) {
            personRepository.eraseFieldsFromDb()
            val personsList = personRepository.read()
            if (personsList?.isSuccessful == true) {
                personsList.body()?.let { responseBody ->
                    val mostPopularPersonId = responseBody.results.maxByOrNull { it.popularity }?.id
                    if (mostPopularPersonId != null) {
                        val detailPersonResponse =
                            personRepository.readDetailPerson(mostPopularPersonId.toString())
                        if (detailPersonResponse?.isSuccessful == true) {
                            detailPersonResponse.body()?.let { detailPerson ->
                                val movieList =
                                    responseBody.results.find { it.id == detailPerson.id }?.knownFor?.map { movie ->
                                        UserMovies(
                                            id = movie.id.toString(),
                                            title = MethodsHandler.validateEmptyField(movie.title),
                                            review = MethodsHandler.validateEmptyField(movie.overview),
                                            posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                                            releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                                            score = movie.voteAverage,
                                        )
                                    }.orEmpty()

                                val mPerson = DetailPersonViewData(
                                    id = detailPerson.id.toString(),
                                    name = MethodsHandler.validateEmptyField(detailPerson.name),
                                    biography = MethodsHandler.validateEmptyField(detailPerson.biography),
                                    birthday = MethodsHandler.validateEmptyField(detailPerson.birthday),
                                    deathday = MethodsHandler.validateEmptyField(detailPerson.deathday),
                                    birthPlace = MethodsHandler.validateEmptyField(detailPerson.placeOfBirth),
                                    popularity = detailPerson.popularity,
                                    profileImagePath = detailPerson.profilePath,
                                    movies = movieList
                                )

                                personRepository.saveInDb(mPerson)
                                return mPerson
                            }
                        }
                    }
                }
            }
        } else {
            try {
                val personsList = personRepository.readFromDb()
                val detailPerson = personsList?.get(0)
                val moviesListType = object : TypeToken<List<UserMovies>>() {}.type
                val moviesList: List<UserMovies>? = Gson().fromJson(detailPerson?.movies ?: "", moviesListType)
                return DetailPersonViewData(
                    id = detailPerson?.id.toString(),
                    name = MethodsHandler.validateEmptyField(detailPerson?.name),
                    biography = MethodsHandler.validateEmptyField(detailPerson?.biography),
                    birthday = MethodsHandler.validateEmptyField(detailPerson?.birthday),
                    deathday = MethodsHandler.validateEmptyField(detailPerson?.deathday),
                    birthPlace = MethodsHandler.validateEmptyField(detailPerson?.birthPlace),
                    popularity = detailPerson?.popularity,
                    profileImagePath = detailPerson?.profileImagePath,
                    movies = moviesList
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SMM_DEBUG", "Error al obtener información de la db. Message: ${e.message}")
            }
        }

        return null
    }

}