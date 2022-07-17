package ru.konstantin.myfilm.model.base

interface Interactor<T> {

    suspend fun getData(title: String, titleType: String, genres: String): T
}