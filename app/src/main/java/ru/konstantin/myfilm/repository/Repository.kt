package ru.konstantin.myfilm.repository

interface Repository<T, D> {

    suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String): T
    suspend fun getResultFilmData(filmId: String): D
}