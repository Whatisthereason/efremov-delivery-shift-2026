package ru.shift.android_intensive_sample

import android.app.Application

class App : Application() {
    val container: AppContainer by lazy { AppContainer() }
}
