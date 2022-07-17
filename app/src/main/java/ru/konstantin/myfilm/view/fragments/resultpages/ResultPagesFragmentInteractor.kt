package ru.konstantin.myfilm.view.fragments.resultpages

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
import org.koin.java.KoinJavaComponent.getKoin

class ResultPagesFragmentInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo, ResultFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = getKoin().get()
    //endregion
    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            AppState {
        val appState: AppState = if (settings.advancedSearchResult.size > 0) {
            val newGeneralFilmInfoList: MutableList<GeneralFilmInfo> = mutableListOf()
            repeat(MAX_NUMBER_FILM_RESULTS_ON_SCREEN) {
                if (settings.advancedSearchResult.size >
                    settings.pagingNumber.getStartElementOnPage() + it)
                    newGeneralFilmInfoList.add(settings.advancedSearchResult[
                            settings.pagingNumber.getStartElementOnPage() + it])
            }
            AppState.SuccessListFilmsInfo(newGeneralFilmInfoList)
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.no_founded_films),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_no_founded_films)))
        }
        return appState
    }
}