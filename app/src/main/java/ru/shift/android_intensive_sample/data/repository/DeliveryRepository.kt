package ru.shift.android_intensive_sample.data.repository

import ru.shift.android_intensive_sample.data.network.DeliveryApi
import ru.shift.android_intensive_sample.data.network.model.CalculateDeliveryDto
import ru.shift.android_intensive_sample.data.network.model.DeliveryOptionDto
import ru.shift.android_intensive_sample.data.network.model.DeliveryPackageTypeDto
import ru.shift.android_intensive_sample.data.network.model.DeliveryPointDto

class DeliveryRepository(
    private val api: DeliveryApi
) {

    suspend fun getPoints(): List<DeliveryPointDto> {
        val response = api.getPoints()
        if (!response.success) {
            throw IllegalStateException(response.reason ?: "Не удалось получить список городов")
        }
        return response.points
    }

    suspend fun getPackageTypes(): List<DeliveryPackageTypeDto> {
        val response = api.getPackageTypes()
        if (!response.success) {
            throw IllegalStateException(response.reason ?: "Не удалось получить список типов посылок")
        }
        return response.packages
    }

    suspend fun calculateDelivery(body: CalculateDeliveryDto): List<DeliveryOptionDto> {
        val response = api.calculateDelivery(body)
        if (!response.success) {
            throw IllegalStateException(response.reason ?: "Не удалось рассчитать доставку")
        }
        return response.options
    }
}
