package ru.shift.android_intensive_sample.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.shift.android_intensive_sample.domain.model.DeliveryOption
import ru.shift.android_intensive_sample.domain.model.OrderDraft

interface OrderDraftRepository {
    val draft: StateFlow<OrderDraft>

    fun setDeliveryOptions(options: List<DeliveryOption>)
    fun selectDeliveryOption(option: DeliveryOption)
    fun clear()
}
