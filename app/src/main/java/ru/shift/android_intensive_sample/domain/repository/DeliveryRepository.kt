package ru.shift.android_intensive_sample.domain.repository

import ru.shift.android_intensive_sample.domain.model.CalculateDeliveryRequest
import ru.shift.android_intensive_sample.domain.model.DeliveryOption
import ru.shift.android_intensive_sample.domain.model.DeliveryPoint
import ru.shift.android_intensive_sample.domain.model.PackageType

interface DeliveryRepository {
    suspend fun getPoints(): List<DeliveryPoint>
    suspend fun getPackageTypes(): List<PackageType>
    suspend fun calculateDelivery(request: CalculateDeliveryRequest): List<DeliveryOption>
}
