package ru.konstantin.myfilm.view.fragments.resultfilm

import android.widget.Toast
import ru.konstantin.myfilm.R
import ru.konstantin.myfilm.model.base.Interactor
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.model.data.DataModelGeneralFilmInfo
import ru.konstantin.myfilm.model.data.GeneralFilmInfo
import ru.konstantin.myfilm.model.data.ResultFilmInfo
import ru.konstantin.myfilm.repository.Repository
import ru.konstantin.myfilm.repository.settings.Settings
import ru.konstantin.myfilm.utils.MAX_NUMBER_FILM_RESULTS_ON_SCREEN
import ru.konstantin.myfilm.utils.getStartElementOnPage
import ru.konstantin.myfilm.utils.network.NetworkStatus
import ru.konstantin.myfilm.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent

class ResultFilmFragmentInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo, ResultFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    //endregion

    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            AppState.SuccessResulFilmInfo(
                remoteRepository.getResultFilmData(settings.idChoosedFilm))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        // Сохранение данных в класс Settings
        (appState as AppState.SuccessResulFilmInfo).resultFilmInfo?.let { resultFilmInfo->
            settings.resultFilmInfo = ResultFilmInfo(
                resultFilmInfo.filmId,
                resultFilmInfo.filmImageLink,
                resultFilmInfo.filmTitle,
                resultFilmInfo.releaseDate,
                resultFilmInfo.filmRunTime,
                resultFilmInfo.filmGenres,
                resultFilmInfo.filmRating,
                resultFilmInfo.filmOverview,
                resultFilmInfo.actorList
            )
        }

        return appState
    }
}