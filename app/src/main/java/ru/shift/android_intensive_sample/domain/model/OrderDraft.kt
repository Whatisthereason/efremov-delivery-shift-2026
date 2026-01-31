package ru.shift.android_intensive_sample.domain.model

data class OrderDraft(
    val deliveryOptions: List<DeliveryOption> = emptyList(),
    val selectedOption: DeliveryOption? = null
)
