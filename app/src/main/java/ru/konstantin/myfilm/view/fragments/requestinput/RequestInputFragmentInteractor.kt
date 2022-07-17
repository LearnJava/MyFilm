package ru.konstantin.myfilm.view.fragments.requestinput

import android.widget.Toast
import ru.konstantin.myfilm.R
import ru.konstantin.myfilm.model.base.Interactor
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.model.data.DataModelGeneralFilmInfo
import ru.konstantin.myfilm.model.data.ResultFilmInfo
import ru.konstantin.myfilm.repository.Repository
import ru.konstantin.myfilm.repository.settings.Settings
import ru.konstantin.myfilm.utils.network.NetworkStatus
import ru.konstantin.myfilm.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent.getKoin

class RequestInputFragmentInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo, ResultFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = getKoin().get()
    //endregion

    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            AppState.Success(remoteRepository.getData(filmTitle, filmTitleType, filmGenre))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        // Сохранение данных в класс Settings
        (appState as AppState.Success).data?.let {
            it.advancedSearchResult?.let { generalFilmInfoList ->
                settings.pagingNumber = 0
                settings.advancedSearchResult.clear()
                generalFilmInfoList.forEach { generalFilmInfo ->
                    settings.advancedSearchResult.add(generalFilmInfo)
                }
            }
        }

        return appState
    }
}