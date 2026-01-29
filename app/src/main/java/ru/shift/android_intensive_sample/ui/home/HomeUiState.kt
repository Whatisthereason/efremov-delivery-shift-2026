package ru.shift.android_intensive_sample.ui.home

data class HomeUiState(
    // выбранные значения
    val fromCity: Option? = null,
    val toCity: Option? = null,
    val packageSize: Option? = null,

    // результат/ошибка расчёта
    val calcResultText: String? = null,
    val calcErrorText: String? = null,

    // Список городов
    val cityOptions: List<Option> = emptyList(),
    val packageSizeOptions: List<Option> = emptyList(),

    // Быстрый выбор города
    val quickCities: List<Option> = emptyList(),

    // Отслеживание
    val trackNumber: String = "",
    val trackResultText: String? = null,
    val trackErrorText: String? = null,
)

data class Option(
    val id: String,
    val title: String
)