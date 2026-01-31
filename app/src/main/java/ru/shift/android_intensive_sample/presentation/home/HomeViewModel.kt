package ru.shift.android_intensive_sample.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.shift.android_intensive_sample.data.network.NetworkModule
import ru.shift.android_intensive_sample.data.repository.DeliveryRepositoryImpl
import ru.shift.android_intensive_sample.domain.model.CalculateDeliveryRequest
import ru.shift.android_intensive_sample.domain.repository.DeliveryRepository
import ru.shift.android_intensive_sample.domain.repository.OrderDraftRepository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.shift.android_intensive_sample.presentation.order.DeliveryOptionUi

class HomeViewModel(
    private val deliveryRepository: DeliveryRepository,
    private val orderDraftRepository: OrderDraftRepository
) : ViewModel() {

    private val repository: DeliveryRepository =
        DeliveryRepositoryImpl(NetworkModule.deliveryApi)

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvent>()
    val events: SharedFlow<HomeEvent> = _events.asSharedFlow()

    sealed interface HomeEvent {
        data object NavigateToOrderStep1 : HomeEvent
    }

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            runCatching {
                kotlinx.coroutines.coroutineScope {
                    val pointsDeferred = async { repository.getPoints() }
                    val packagesDeferred = async { repository.getPackageTypes() }

                    val points = pointsDeferred.await()
                    val packages = packagesDeferred.await()

                    _state.update {
                        it.copy(
                            isLoading = false,
                            cityOptions = points.map { p -> Option(p.id, p.name) },
                            packageSizeOptions = packages.map { p -> Option(p.id, p.name) },
                            quickCities = points.take(3).map { p -> Option(p.id, p.name) }
                        )
                    }
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = HomeError.Network(e.message)
                    )
                }
            }
        }
    }

    fun onFromCitySelected(option: Option) {
        _state.update {
            it.copy(fromCity = option, error = null, calcResultText = null)
        }
    }

    fun onToCitySelected(option: Option) {
        _state.update {
            it.copy(toCity = option, error = null, calcResultText = null)
        }
    }

    fun onPackageSizeSelected(option: Option) {
        _state.update {
            it.copy(packageSize = option, error = null, calcResultText = null)
        }
    }

    fun calculateDelivery() {
        val s = _state.value

        val from = s.fromCity ?: run {
            setCalcError(HomeError.SelectFromCity); return
        }
        val to = s.toCity ?: run {
            setCalcError(HomeError.SelectToCity); return
        }
        val pkg = s.packageSize ?: run {
            setCalcError(HomeError.SelectPackageSize); return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, calcResultText = null) }

            runCatching {
                repository.calculateDelivery(
                    CalculateDeliveryRequest(
                        packageTypeId = pkg.id,
                        senderPointId = from.id,
                        receiverPointId = to.id
                    )
                )
            }.onSuccess { options ->
                orderDraftRepository.setDeliveryOptions(options)
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _events.emit(HomeEvent.NavigateToOrderStep1)
            }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = HomeError.Network(e.message)
                        )
                    }
            }
        }
    }

    fun onTrackNumberChange(value: String) {
        _state.update {
            it.copy(trackNumber = value.trim(), trackError = null, trackResultText = null)
        }
    }

    fun onFindClick() {
        val s = _state.value
        if (s.trackNumber.isBlank()) {
            _state.update { it.copy(trackError = HomeError.EnterTrackNumber, trackResultText = null) }
            return
        }

        _state.update {
            it.copy(trackError = null, trackResultText = "Поиск заказа: ${s.trackNumber} (заглушка)")
        }
    }

    private fun setCalcError(error: HomeError) {
        _state.update { it.copy(error = error, calcResultText = null) }
    }
}
