package ru.konstantin.myfilm.model.base

import androidx.lifecycle.MutableLiveData
import ru.konstantin.myfilm.repository.settings.Settings
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import ru.konstantin.myfilm.model.data.AppState
import ru.konstantin.myfilm.navigator.AppScreens
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent

abstract class BaseViewModel<T: AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData(),
    protected open val _mutableLiveDataSettings: MutableLiveData<Settings> = MutableLiveData()
): ViewModel() {
    /** Исходные данные */ //region
    // Навигация
    val screens: AppScreens = KoinJavaComponent.getKoin().get()
    val router: Router = KoinJavaComponent.getKoin().get()
    //endregion

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getData(filmTitle: String, filmTitleType: String, filmGenre: String)

    abstract fun handleError(error: Throwable)
}