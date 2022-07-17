package ru.konstantin.myfilm.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.konstantin.myfilm.view.fragments.requestinput.RequestInputFragment
import ru.konstantin.myfilm.view.fragments.resultfilm.ResultFilmFragment
import ru.konstantin.myfilm.view.fragments.resultpages.ResultPagesFragment

class AppScreensImpl: AppScreens {
    // Окно с запросом фильмов
    override fun requestInputScreen() = FragmentScreen {
        RequestInputFragment.newInstance()
    }

    // Окно с результатами запроса фильмов
    override fun resultPagesScreen() = FragmentScreen {
        ResultPagesFragment.newInstance()
    }

    // Окно с детальной информацией по выбранному фильму
    override fun resultFilmScreen() = FragmentScreen {
        ResultFilmFragment.newInstance()
    }
}