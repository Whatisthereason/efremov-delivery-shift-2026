package ru.shift.android_intensive_sample.domain.model

data class CalculateDeliveryRequest(
    val packageTypeId: String,
    val senderPointId: String,
    val receiverPointId: String
)
