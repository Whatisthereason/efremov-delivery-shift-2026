package ru.shift.android_intensive_sample.presentation.order

import androidx.annotation.StringRes

data class OrderStep1UiState(
    val options: List<DeliveryOptionItem> = emptyList(),
    val selectedType: String? = null
)

data class DeliveryOptionItem(
    val type: String,
    @StringRes val titleRes: Int,
    val price: Int,
    val days: Int
)
