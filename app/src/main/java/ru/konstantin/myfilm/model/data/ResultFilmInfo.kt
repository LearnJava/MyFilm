package ru.konstantin.myfilm.model.data

import com.google.gson.annotations.SerializedName

class ResultFilmInfo(
    @field:SerializedName("id") val filmId: String?,
    @field:SerializedName("image") val filmImageLink: String?,
    @field:SerializedName("title") val filmTitle: String?,
    @field:SerializedName("releaseDate") val releaseDate: String?,
    @field:SerializedName("runtimeStr") val filmRunTime: String?,
    @field:SerializedName("genres") val filmGenres: String?,
    @field:SerializedName("imDbRating") val filmRating: String?,
    @field:SerializedName("plot") val filmOverview: String?,
    @field:SerializedName("actorList") val actorList: List<ActorInfo>?
)