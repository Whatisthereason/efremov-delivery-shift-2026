package ru.shift.android_intensive_sample.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.shift.android_intensive_sample.data.network.NetworkModule
import ru.shift.android_intensive_sample.data.repository.DeliveryRepository

class HomeViewModel : ViewModel() {

    private val repository =
        DeliveryRepository(NetworkModule.deliveryApi)

    private val _state = mutableStateOf(HomeUiState())
    val state: State<HomeUiState> = _state

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            runCatching {
                val points = repository.getPoints()
                val packages = repository.getPackageTypes()

                _state.value = _state.value.copy(
                    cityOptions = points.map { Option(it.id, it.name) },
                    packageSizeOptions = packages.map { Option(it.id, it.name) },
                    quickCities = points.take(3).map { Option(it.id, it.name) }
                )
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    calcErrorText = e.message ?: "Ошибка загрузки данных"
                )
            }
        }
    }

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

        // Временная заглушка, будет заменена на реальный /calc
        _state.value = s.copy(
            calcErrorText = null,
            calcResultText = "Расчёт выполнен (заглушка)"
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
            _state.value = s.copy(
                trackErrorText = "Введите номер заказа",
                trackResultText = null
            )
            return
        }

        // Заглушка отслеживания
        _state.value = s.copy(
            trackErrorText = null,
            trackResultText = "Поиск заказа: ${s.trackNumber} (заглушка)"
        )
    }
}
