package ru.shift.android_intensive_sample.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalculateDeliveryDto(
    @SerialName("package") val pkg: CalculateDeliveryPackageDto,
    val senderPoint: CalculateDeliveryPointDto,
    val receiverPoint: CalculateDeliveryPointDto
)

@Serializable
data class CalculateDeliveryPackageDto(
    val length: Double,
    val width: Double,
    val height: Double,
    val weight: Double
)

@Serializable
data class CalculateDeliveryPointDto(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class CalculateDeliveryResponse(
    val success: Boolean,
    val reason: String? = null,
    val options: List<DeliveryOptionDto> = emptyList()
)

@Serializable
data class DeliveryOptionDto(
    val type: String,
    val price: Int,
    val days: Int
)
