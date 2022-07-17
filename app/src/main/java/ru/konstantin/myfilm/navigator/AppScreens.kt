package ru.konstantin.myfilm.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen

interface AppScreens {
    fun requestInputScreen(): FragmentScreen
    fun resultPagesScreen(): FragmentScreen
    fun resultFilmScreen(): FragmentScreen
}