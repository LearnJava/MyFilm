package ru.konstantin.myfilm.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NetworkStatus(
    context: Context
) {
    /** Исходные данные */ //region
    // BehaviorSubject
    private val networkSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()
    // ConnectivityManager
    private val connectivityManager: ConnectivityManager? =
        context.getSystemService<ConnectivityManager>()
    //endregion

    fun getNetworkSubject(): Observable<Boolean> {
        return networkSubject
    }

    fun isOnline() = networkSubject.value ?: false

    init {
        val request = NetworkRequest.Builder().build()

        connectivityManager?.registerNetworkCallback(
            request,
            object: ConnectivityManager.NetworkCallback() {
                /** Сеть есть уже сейчас */
                override fun onAvailable(network: Network) {
                    networkSubject.onNext(true)
                }

                /** Сеть потеряна */
                override fun onLost(network: Network) {
                    networkSubject.onNext(false)
                }

                /** Сеть не обнаружена после запроса */
                override fun onUnavailable() {
                    networkSubject.onNext(false)
                }
            })
    }
}