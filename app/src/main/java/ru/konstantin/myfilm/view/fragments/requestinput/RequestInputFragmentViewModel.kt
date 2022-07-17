package ru.konstantin.myfilm.view.fragments.requestinput

import androidx.lifecycle.LiveData
import ru.konstantin.myfilm.model.base.BaseViewModel
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.repository.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RequestInputFragmentViewModel(
    private val interactor: RequestInputFragmentInteractor,
    private val settings: Settings
): BaseViewModel<AppState>() {
    /** Задание исходных данных */ //region
    // Информация с результатом запроса
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    //endregion

    override fun getData(filmTitle: String, filmTitleType: String, filmGenre: String) {
        // Сохранение данных о запросе в класс Settings
        settings.filmTitle = filmTitle
        settings.filmTitleType = filmTitleType
        settings.filmGenre = filmGenre

        // Выполнение поиска
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(filmTitle, filmTitleType, filmGenre)
        }
    }

    private suspend fun startInteractor(filmTitle: String, filmTitleType: String, filmGenre: String) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(interactor.getData(filmTitle, filmTitleType, filmGenre))
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
}