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
        // Важно: чтобы собрать CalculateDeliveryDto, нужны координаты и размеры.
        // Поэтому берём справочники и находим нужные элементы по id.
        val points = getPoints()
        val packages = getPackageTypes()

        val sender = points.firstOrNull { it.id == request.senderPointId }
            ?: throw IllegalStateException("Не найден пункт отправки")

        val receiver = points.firstOrNull { it.id == request.receiverPointId }
            ?: throw IllegalStateException("Не найден пункт назначения")

        val pkgType = packages.firstOrNull { it.id == request.packageTypeId }
            ?: throw IllegalStateException("Не найден тип посылки")

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
            throw IllegalStateException(response.reason ?: "Не удалось рассчитать доставку")
        }

        return response.options.map { dto ->
            DeliveryOption(
                type = dto.type,
                price = dto.price,
                days = dto.days
            )
        }
    }
}
