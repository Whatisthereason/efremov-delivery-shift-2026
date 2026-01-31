package ru.shift.android_intensive_sample.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.shift.android_intensive_sample.domain.model.DeliveryOption
import ru.shift.android_intensive_sample.domain.model.OrderDraft
import ru.shift.android_intensive_sample.domain.repository.OrderDraftRepository

class OrderDraftRepositoryImpl : OrderDraftRepository {

    private val _draft = MutableStateFlow(OrderDraft())
    override val draft: StateFlow<OrderDraft> = _draft.asStateFlow()

    override fun setDeliveryOptions(options: List<DeliveryOption>) {
        _draft.value = _draft.value.copy(
            deliveryOptions = options,
            selectedOption = options.minByOrNull { it.price }
        )
    }

    override fun selectDeliveryOption(option: DeliveryOption) {
        _draft.value = _draft.value.copy(selectedOption = option)
    }

    override fun clear() {
        _draft.value = OrderDraft()
    }
}
