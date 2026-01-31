package ru.shift.android_intensive_sample.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.shift.android_intensive_sample.data.network.model.CalculateDeliveryDto
import ru.shift.android_intensive_sample.data.network.model.DeliveryPackageTypesResponse
import ru.shift.android_intensive_sample.data.network.model.DeliveryPointsResponse
import ru.shift.android_intensive_sample.data.network.model.CalculateDeliveryResponse

interface DeliveryApi {

    @GET("api/delivery/points")
    suspend fun getPoints(): DeliveryPointsResponse

    @GET("api/delivery/package/types")
    suspend fun getPackageTypes(): DeliveryPackageTypesResponse

    @POST("api/delivery/calc")
    suspend fun calculateDelivery(
        @Body body: CalculateDeliveryDto
    ): CalculateDeliveryResponse
}