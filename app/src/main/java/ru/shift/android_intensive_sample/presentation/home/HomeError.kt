package ru.shift.android_intensive_sample.presentation.home

sealed interface HomeError {

    data object SelectFromCity : HomeError
    data object SelectToCity : HomeError
    data object SelectPackageSize : HomeError
    data object EnterTrackNumber : HomeError

    data class Network(val message: String? = null) : HomeError
}


