package ru.shift.android_intensive_sample.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Scaffold
import ru.shift.android_intensive_sample.ui.navigation.BottomBar
import ru.shift.android_intensive_sample.ui.navigation.BottomTab


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.state
    var selectedTab by remember { mutableStateOf(BottomTab.CALC) }
    Scaffold(
        bottomBar = {
            BottomBar(
                selected = selectedTab,
                onSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedTab) {
                BottomTab.CALC -> {
                    Text(
                        "Мы доставим\nваш заказ",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        "Отправляйте посылки в приложении\nШифт-Интенсив Delivery",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    CalculateDeliveryCard(
                        state = state,
                        onFromSelected = viewModel::onFromCitySelected,
                        onToSelected = viewModel::onToCitySelected,
                        onPackageSelected = viewModel::onPackageSizeSelected,
                        onQuickFromClick = viewModel::onQuickFromCityClick,
                        onQuickToClick = viewModel::onQuickToCityClick,
                        onCalculateClick = viewModel::onCalculateClick
                    )

                    TrackDeliveryCard(
                        trackNumber = state.trackNumber,
                        errorText = state.trackErrorText,
                        resultText = state.trackResultText,
                        onTrackNumberChange = viewModel::onTrackNumberChange,
                        onFindClick = viewModel::onFindClick
                    )

                }

                BottomTab.HISTORY -> {
                    Text("История (заглушка)", style = MaterialTheme.typography.titleLarge)
                }

                BottomTab.PROFILE -> {
                    Text("Профиль (заглушка)", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

    @Composable
    private fun CalculateDeliveryCard(
        state: HomeUiState,
        onFromSelected: (Option) -> Unit,
        onToSelected: (Option) -> Unit,
        onPackageSelected: (Option) -> Unit,
        onQuickFromClick: (Option) -> Unit,
        onQuickToClick: (Option) -> Unit,
        onCalculateClick: () -> Unit
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Рассчитать доставку", style = MaterialTheme.typography.titleLarge)

                Text(
                    "Город отправки",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SimpleDropdown(
                    selected = state.fromCity?.title ?: "",
                    placeholder = "Выберите город",
                    options = state.cityOptions,
                    onSelect = onFromSelected
                )

                QuickLinksRow(
                    options = state.quickCities,
                    onClick = onQuickFromClick
                )

                Text(
                    "Город назначения",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SimpleDropdown(
                    selected = state.toCity?.title ?: "",
                    placeholder = "Выберите город",
                    options = state.cityOptions,
                    onSelect = onToSelected
                )

                // Быстрый выбор города:
                QuickLinksRow(
                    options = listOf(
                        Option("nsk", "Новосибирск"),
                        Option("tmk", "Томск"),
                        Option("msk", "Москва")
                    ),
                    onClick = onQuickToClick
                )

                Text(
                    "Размер посылки",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                SimpleDropdown(
                    selected = state.packageSize?.title ?: "",
                    placeholder = "Выберите размер",
                    options = state.packageSizeOptions,
                    onSelect = onPackageSelected
                )

                Button(
                    onClick = onCalculateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Рассчитать")
                }

                state.calcErrorText?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
                state.calcResultText?.let {
                    Text(it, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }


    @Composable
    private fun TrackDeliveryCard(
        trackNumber: String,
        errorText: String?,
        resultText: String?,
        onTrackNumberChange: (String) -> Unit,
        onFindClick: () -> Unit
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Отследить посылку", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = trackNumber,
                    onValueChange = onTrackNumberChange,
                    label = { Text("Номер заказа") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = onFindClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Найти")
                }

                errorText?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                resultText?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            }
        }
    }

    @Composable
    private fun QuickLinksRow(
        options: List<Option>,
        onClick: (Option) -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { opt ->
                Text(
                    text = opt.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onClick(opt) }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SimpleDropdown(
        selected: String,
        placeholder: String,
        options: List<Option>,
        onSelect: (Option) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(placeholder) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt.title) },
                        onClick = {
                            onSelect(opt)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun PlaceholderScreen(title: String) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
