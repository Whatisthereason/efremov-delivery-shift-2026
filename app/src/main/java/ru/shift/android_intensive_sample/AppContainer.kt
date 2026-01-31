package ru.shift.android_intensive_sample

import ru.shift.android_intensive_sample.data.network.NetworkModule
import ru.shift.android_intensive_sample.data.repository.DeliveryRepositoryImpl
import ru.shift.android_intensive_sample.data.repository.OrderDraftRepositoryImpl
import ru.shift.android_intensive_sample.domain.repository.DeliveryRepository
import ru.shift.android_intensive_sample.domain.repository.OrderDraftRepository

class AppContainer {

    val deliveryRepository: DeliveryRepository =
        DeliveryRepositoryImpl(NetworkModule.deliveryApi)

    val orderDraftRepository: OrderDraftRepository =
        OrderDraftRepositoryImpl()
}
