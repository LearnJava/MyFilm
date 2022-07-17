package ru.konstantin.myfilm.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import ru.konstantin.myfilm.model.data.DataModelGeneralFilmInfo
import ru.konstantin.myfilm.model.data.ResultFilmInfo
import ru.konstantin.myfilm.navigator.AppScreens
import ru.konstantin.myfilm.navigator.AppScreensImpl
import ru.konstantin.myfilm.repository.Repository
import ru.konstantin.myfilm.repository.RepositoryImplementation
import ru.konstantin.myfilm.repository.datasource.RetrofitImplementation
import ru.konstantin.myfilm.repository.settings.Settings
import ru.konstantin.myfilm.utils.*
import ru.konstantin.myfilm.utils.imageloader.GlideImageLoaderImpl
import ru.konstantin.myfilm.utils.network.NetworkStatus
import ru.konstantin.myfilm.utils.resources.ResourcesProviderImpl
import ru.konstantin.myfilm.utils.themecolors.ThemeColorsImpl
import ru.konstantin.myfilm.view.activity.MainViewModel
import ru.konstantin.myfilm.view.fragments.requestinput.RequestInputFragmentInteractor
import ru.konstantin.myfilm.view.fragments.requestinput.RequestInputFragmentViewModel
import ru.konstantin.myfilm.view.fragments.resultfilm.ResultFilmFragmentInteractor
import ru.konstantin.myfilm.view.fragments.resultfilm.ResultFilmFragmentViewModel
import ru.konstantin.myfilm.view.fragments.resultpages.ResultPagesFragmentInteractor
import ru.konstantin.myfilm.view.fragments.resultpages.ResultPagesFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val application = module {
    // Удалённый сервер (API)
    single<Repository<DataModelGeneralFilmInfo, ResultFilmInfo>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    // Локальное сохранение данных
    single<Settings> { Settings() }

    // Навигация между окнами
    single<Cicerone<Router>>(named(CICERONE_NAME)) { Cicerone.create() }
    single<NavigatorHolder> {
        get<Cicerone<Router>>(named(CICERONE_NAME)).getNavigatorHolder() }
    single<Router> { get<Cicerone<Router>>(named(CICERONE_NAME)).router }
    single<AppScreens> { AppScreensImpl() }

    // Вспомогательные классы:
    // Определение статуса сети
    single<NetworkStatus> { NetworkStatus(androidContext()) }
    // Получение доступа к ресурсам
    single<ResourcesProviderImpl> { ResourcesProviderImpl(androidContext()) }
    // Получение доступа к цветам темы
    single<ThemeColorsImpl> { ThemeColorsImpl() }
    // Загрузка изображений с помощью библиотеки Glide
    single<GlideImageLoaderImpl> { GlideImageLoaderImpl() }
}

val screens = module {
    // Scope для MainActivity
    scope(named(MAIN_ACTIVITY_SCOPE)) {
        viewModel {
            MainViewModel()
        }
    }

    // Scope для фрагмента с поисковым запросом
    scope(named(REQUEST_INPUT_FRAGMENT_SCOPE)) {
        scoped {
            RequestInputFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            RequestInputFragmentViewModel(getScope(REQUEST_INPUT_FRAGMENT_SCOPE).get(), get())
        }
    }

    // Scope для фрагмента со списком найденных фильмов для создания пагинации
    scope(named(RESULT_PAGES_FRAGMENT_SCOPE)) {
        scoped {
            ResultPagesFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            ResultPagesFragmentViewModel(getScope(RESULT_PAGES_FRAGMENT_SCOPE).get(), get())
        }
    }

    // Scope для фрагмента с детальной информацией о выбранном фильме
    scope(named(RESULT_FILM_FRAGMENT_SCOPE)) {
        scoped {
            ResultFilmFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            ResultFilmFragmentViewModel(getScope(RESULT_FILM_FRAGMENT_SCOPE).get(), get())
        }
    }
}