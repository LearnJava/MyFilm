package ru.konstantin.myfilm.repository

import ru.konstantin.myfilm.model.data.DataModelGeneralFilmInfo
import ru.konstantin.myfilm.model.data.ResultFilmInfo
import ru.konstantin.myfilm.repository.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<DataModelGeneralFilmInfo, ResultFilmInfo>
): Repository<DataModelGeneralFilmInfo, ResultFilmInfo> {

    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            DataModelGeneralFilmInfo {
        return dataSource.getData(filmTitle, filmTitleType, filmGenre)
    }

    override suspend fun getResultFilmData(filmId: String): ResultFilmInfo {
        return dataSource.getResultFilmData(filmId)
    }
}