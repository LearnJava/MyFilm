package ru.konstantin.myfilm.model.data

import com.google.gson.annotations.SerializedName

class GeneralFilmInfo(
    @field:SerializedName("id") val filmId: String?,
    @field:SerializedName("image") val filmImageLink: String?,
    @field:SerializedName("title") val filmTitle: String?,
    @field:SerializedName("imDbRating") val filmRating: String?,
    @field:SerializedName("description", alternate = ["year"]) val filmData: String?,
)