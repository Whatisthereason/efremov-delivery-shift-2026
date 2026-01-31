package ru.shift.android_intensive_sample.presentation.home
import ru.shift.android_intensive_sample.presentation.order.DeliveryOptionUi

data class HomeUiState(

    val fromCity: Option? = null,
    val toCity: Option? = null,
    val packageSize: Option? = null,

    val calcResultText: String? = null,

    val deliveryOptions: List<DeliveryOptionUi> = emptyList(),
    val selectedDeliveryOption: DeliveryOptionUi? = null,

    val error: HomeError? = null,

    val isLoading: Boolean = false,

    val cityOptions: List<Option> = emptyList(),
    val packageSizeOptions: List<Option> = emptyList(),
    val quickCities: List<Option> = emptyList(),

    val trackNumber: String = "",
    val trackResultText: String? = null,
    val trackError: HomeError? = null,
)

data class Option(
    val id: String,
    val title: String
)
