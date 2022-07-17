package ru.konstantin.myfilm.model.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import ru.konstantin.myfilm.navigator.AppScreens
import ru.konstantin.myfilm.navigator.AppScreensImpl
import org.koin.java.KoinJavaComponent.getKoin

abstract class BaseViewModelForNavigation: ViewModel() {
    /** Исходные данные */ //region
    // Навигация
    val screens: AppScreens = getKoin().get()
    val router: Router = getKoin().get()
    //endregion
}