package ru.shift.android_intensive_sample.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val moscow = Option("msk", "Москва")
    private val spb = Option("spb", "Санкт-Петербург")
    private val nsk = Option("nsk", "Новосибирск")
    private val tomsk = Option("tmk", "Томск")

    private val _state = mutableStateOf(
        HomeUiState(
            cityOptions = listOf(moscow, spb, nsk, tomsk),
            quickCities = listOf(spb, nsk, tomsk), // на макете под Москвой видны три “быстрых”
            packageSizeOptions = listOf(
                Option("envelope", "Конверт"),
                Option("small", "Маленькая"),
                Option("medium", "Средняя"),
                Option("large", "Большая"),
            ),
            fromCity = moscow, // как на макете: по умолчанию Москва
            toCity = spb,      // как на макете: по умолчанию Санкт-Петербург
            packageSize = Option("envelope", "Конверт"),
        )
    )
    val state: State<HomeUiState> = _state

    fun onFromCitySelected(option: Option) {
        _state.value = _state.value.copy(fromCity = option, calcErrorText = null)
    }

    fun onToCitySelected(option: Option) {
        _state.value = _state.value.copy(toCity = option, calcErrorText = null)
    }

    fun onPackageSizeSelected(option: Option) {
        _state.value = _state.value.copy(packageSize = option, calcErrorText = null)
    }

    fun onQuickFromCityClick(option: Option) = onFromCitySelected(option)
    fun onQuickToCityClick(option: Option) = onToCitySelected(option)

    fun onCalculateClick() {
        val s = _state.value

        if (s.fromCity == null) {
            _state.value = s.copy(calcErrorText = "Выберите город отправки", calcResultText = null)
            return
        }
        if (s.toCity == null) {
            _state.value = s.copy(calcErrorText = "Выберите город назначения", calcResultText = null)
            return
        }
        if (s.packageSize == null) {
            _state.value = s.copy(calcErrorText = "Выберите размер посылки", calcResultText = null)
            return
        }

        // Черновой расчёт: просто чтобы было что показать
        val price = when (s.packageSize.id) {
            "envelope" -> 199
            "small" -> 249
            "medium" -> 349
            else -> 449
        }

        _state.value = s.copy(
            calcErrorText = null,
            calcResultText = "Стоимость: $price ₽"
        )
    }
    fun onTrackNumberChange(value: String) {
        _state.value = _state.value.copy(
            trackNumber = value.trim(),
            trackErrorText = null,
            trackResultText = null
        )
    }

    fun onFindClick() {
        val s = _state.value
        if (s.trackNumber.isBlank()) {
            _state.value = s.copy(trackErrorText = "Введите номер заказа", trackResultText = null)
            return
        }

        // Заглушка на лёгком уровне (позже можно заменить на реальный API, если потребуется)
        _state.value = s.copy(
            trackErrorText = null,
            trackResultText = "Поиск заказа: ${s.trackNumber} (заглушка)"
        )
    }
}
