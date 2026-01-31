package ru.shift.android_intensive_sample.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DeliveryPointsResponse(
    val success: Boolean,
    val reason: String? = null,
    val points: List<DeliveryPointDto> = emptyList()
)

@Serializable
data class DeliveryPointDto(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
)
