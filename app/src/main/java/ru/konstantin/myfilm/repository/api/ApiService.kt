package ru.konstantin.myfilm.repository.api

import ru.konstantin.myfilm.model.data.DataModelGeneralFilmInfo
import ru.konstantin.myfilm.model.data.ResultFilmInfo
import ru.konstantin.myfilm.utils.IMDB_KEY_VALUE
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(IMDB_KEY_VALUE)
    fun advancedSearchAsync(@Query("title") title: String,
                            @Query("title_type") titleType: String,
                            @Query("genres") genres: String,
                            @Query("count") maxCountSearchedFilms: Int
    ): Deferred<DataModelGeneralFilmInfo>

    @GET(IMDB_KEY_VALUE)
    fun topSearchAsync(): Deferred<DataModelGeneralFilmInfo>

    @GET("$IMDB_KEY_VALUE/{film_id}")
    fun resultFilmSearchAsync(@Path("film_id") filmId: String
    ): Deferred<ResultFilmInfo>
}