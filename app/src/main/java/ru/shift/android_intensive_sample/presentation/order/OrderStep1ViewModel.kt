package ru.shift.android_intensive_sample.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.shift.android_intensive_sample.R
import ru.shift.android_intensive_sample.domain.model.DeliveryOption
import ru.shift.android_intensive_sample.domain.repository.OrderDraftRepository

class OrderStep1ViewModel(
    private val orderDraftRepository: OrderDraftRepository
) : ViewModel() {

    val state: StateFlow<OrderStep1UiState> =
        orderDraftRepository.draft
            .map { draft ->
                OrderStep1UiState(
                    options = draft.deliveryOptions.map { it.toItem() },
                    selectedType = draft.selectedOption?.type
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = OrderStep1UiState()
            )

    fun onOptionSelected(type: String) {
        val option = orderDraftRepository.draft.value.deliveryOptions
            .firstOrNull { it.type == type }
            ?: return

        orderDraftRepository.selectDeliveryOption(option)
    }

    private fun DeliveryOption.toItem(): DeliveryOptionItem {
        return DeliveryOptionItem(
            type = type,
            titleRes = type.toTitleRes(),
            price = price,
            days = days
        )
    }

    private fun String.toTitleRes(): Int {
        return when (uppercase()) {
            "DEFAULT" -> R.string.delivery_type_default
            "EXPRESS" -> R.string.delivery_type_express
            else -> R.string.delivery_type_unknown
        }
    }
}
