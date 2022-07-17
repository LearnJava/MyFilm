package ru.konstantin.myfilm.model.data

sealed class AppState {
    data class Success(val data: DataModelGeneralFilmInfo?): AppState()
    data class SuccessListFilmsInfo(val generalFilmInfoList: List<GeneralFilmInfo>?): AppState()
    data class SuccessResulFilmInfo(val resultFilmInfo: ResultFilmInfo?): AppState()
    data class Error(val error: Throwable): AppState()
    data class Loading(val progress: Int?): AppState()
}