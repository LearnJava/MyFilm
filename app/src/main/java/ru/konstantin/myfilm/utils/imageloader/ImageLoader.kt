package ru.konstantin.myfilm.utils.imageloader

interface ImageLoader<T> {
    fun loadInto(url: String, container: T)
}