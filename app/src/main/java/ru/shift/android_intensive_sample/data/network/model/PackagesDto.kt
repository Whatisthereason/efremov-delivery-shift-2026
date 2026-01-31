package ru.shift.android_intensive_sample.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
