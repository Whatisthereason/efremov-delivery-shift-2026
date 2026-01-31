package ru.shift.android_intensive_sample.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.shift.android_intensive_sample.domain.repository.OrderDraftRepository

class OrderStep1ViewModelFactory(
    private val orderDraftRepository: OrderDraftRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderStep1ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderStep1ViewModel(orderDraftRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
