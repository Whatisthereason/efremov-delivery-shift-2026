package ru.shift.android_intensive_sample.ui.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.shift.android_intensive_sample.App
import ru.shift.android_intensive_sample.R
import ru.shift.android_intensive_sample.presentation.order.OrderStep1ViewModel
import ru.shift.android_intensive_sample.presentation.order.OrderStep1ViewModelFactory

@Composable
fun OrderStep1Screen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as App
    val container = app.container

    val viewModel: OrderStep1ViewModel = viewModel(
        factory = OrderStep1ViewModelFactory(container.orderDraftRepository)
    )

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.back))
            }
            Text(
                text = stringResource(R.string.order_step1_title),
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (state.options.isEmpty()) {
            Text(
                text = stringResource(R.string.order_step1_no_data),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            return@Column
        }

        Text(
            text = stringResource(R.string.order_step1_subtitle),
            style = MaterialTheme.typography.titleMedium
        )

        state.options.forEach { opt ->
            val selected = state.selectedType == opt.type
            DeliveryOptionCard(
                title = stringResource(opt.titleRes),
                price = opt.price,
                days = opt.days,
                selected = selected,
                onClick = { viewModel.onOptionSelected(opt.type) }
            )
        }
    }
}

@Composable
private fun DeliveryOptionCard(
    title: String,
    price: Int,
    days: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val border = if (selected) {
        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    } else {
        null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        border = border
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text("$price ₽", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = stringResource(R.string.order_step1_days, days),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
