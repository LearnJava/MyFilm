package ru.konstantin.myfilm.repository.datasource

interface DataSource<T, D> {

    suspend fun getData(title: String, titleType: String, genres: String): T
    suspend fun getResultFilmData(filmId: String): D
}