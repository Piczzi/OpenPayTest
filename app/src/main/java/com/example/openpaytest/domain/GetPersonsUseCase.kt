package com.example.openpaytest.domain

import com.example.openpaytest.data.model.local.DetailPersonViewData
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.data.repositories.PersonRepository
import com.example.openpaytest.utils.MethodsHandler
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val personRepository: PersonRepository,
) {

    suspend operator fun invoke(): DetailPersonViewData? {
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
                                        title = movie.title.orEmpty(),
                                        review = movie.overview.orEmpty(),
                                        posterPath = movie.posterPath.orEmpty(),
                                    )
                                }.orEmpty()

                            return DetailPersonViewData(
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
                        }
                    }
                }
            }
        }

        return null
    }

}