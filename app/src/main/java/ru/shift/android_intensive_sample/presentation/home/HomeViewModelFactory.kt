package ru.shift.android_intensive_sample.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.shift.android_intensive_sample.domain.repository.DeliveryRepository
import ru.shift.android_intensive_sample.domain.repository.OrderDraftRepository

class HomeViewModelFactory(
    private val deliveryRepository: DeliveryRepository,
    private val orderDraftRepository: OrderDraftRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                deliveryRepository = deliveryRepository,
                orderDraftRepository = orderDraftRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
