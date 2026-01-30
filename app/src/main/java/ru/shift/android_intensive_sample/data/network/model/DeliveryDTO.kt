package ru.shift.android_intensive_sample.data.network.model

import kotlinx.serialization.SerialName
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

@Serializable
data class DeliveryPackageTypesResponse(
    val success: Boolean,
    val reason: String? = null,
    @SerialName("packages") val packages: List<DeliveryPackageTypeDto> = emptyList()
)

@Serializable
data class DeliveryPackageTypeDto(
    val id: String,
    val name: String,
    val length: Double,
    val width: Double,
    val height: Double,
    val weight: Double
)

// --- calc ---

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