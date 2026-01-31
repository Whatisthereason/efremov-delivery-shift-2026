package ru.shift.android_intensive_sample.data.repository

import ru.shift.android_intensive_sample.data.network.DeliveryApi
import ru.shift.android_intensive_sample.data.network.model.CalculateDeliveryDto
import ru.shift.android_intensive_sample.data.network.model.CalculateDeliveryPackageDto
import ru.shift.android_intensive_sample.data.network.model.CalculateDeliveryPointDto
import ru.shift.android_intensive_sample.domain.model.CalculateDeliveryRequest
import ru.shift.android_intensive_sample.domain.model.DeliveryOption
import ru.shift.android_intensive_sample.domain.model.DeliveryPoint
import ru.shift.android_intensive_sample.domain.model.PackageType
import ru.shift.android_intensive_sample.domain.repository.DeliveryRepository

class DeliveryRepositoryImpl(
    private val api: DeliveryApi
) : DeliveryRepository {

    override suspend fun getPoints(): List<DeliveryPoint> {
        val response = api.getPoints()
        if (!response.success) {
            throw IllegalStateException(response.reason ?: "Не удалось получить список городов")
        }
        return response.points.map { dto ->
            DeliveryPoint(
                id = dto.id,
                name = dto.name,
                latitude = dto.latitude,
                longitude = dto.longitude
            )
        }
    }

    override suspend fun getPackageTypes(): List<PackageType> {
        val response = api.getPackageTypes()
        if (!response.success) {
            throw IllegalStateException(response.reason ?: "Не удалось получить список типов посылок")
        }
        return response.packages.map { dto ->
            PackageType(
                id = dto.id,
                name = dto.name,
                length = dto.length,
                width = dto.width,
                height = dto.height,
                weight = dto.weight
            )
        }
    }

    override suspend fun calculateDelivery(request: CalculateDeliveryRequest): List<DeliveryOption> {

        val points = getPoints()
        val packages = getPackageTypes()

        val sender = points.firstOrNull { it.id == request.senderPointId }
            ?: throw IllegalStateException("SENDER_POINT_NOT_FOUND")

        val receiver = points.firstOrNull { it.id == request.receiverPointId }
            ?: throw IllegalStateException("RECEIVER_POINT_NOT_FOUND")

        val pkgType = packages.firstOrNull { it.id == request.packageTypeId }
            ?: throw IllegalStateException("PACKAGE_TYPE_NOT_FOUND")

        val body = CalculateDeliveryDto(
            pkg = CalculateDeliveryPackageDto(
                length = pkgType.length,
                width = pkgType.width,
                height = pkgType.height,
                weight = pkgType.weight
            ),
            senderPoint = CalculateDeliveryPointDto(
                latitude = sender.latitude,
                longitude = sender.longitude
            ),
            receiverPoint = CalculateDeliveryPointDto(
                latitude = receiver.latitude,
                longitude = receiver.longitude
            )
        )

        val response = api.calculateDelivery(body)
        if (!response.success) {
            throw IllegalStateException(response.reason ?: "CALCULATION_FAILED")
        }

        return response.options.map { dto ->
            DeliveryOption(
                type = dto.type,
                //С сервера же приходят копейки? Иначе сильно дорого (в описании API не нашел)
                price = dto.price / 100,
                days = dto.days
            )
        }
    }
}
