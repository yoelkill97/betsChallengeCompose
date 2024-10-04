package com.example.betschallenge.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betschallenge.presentation.ui.theme.BlackPrimary


@Composable
fun FilterSection(
    selectedStatus: String?,
    selectedType: String?,
    onStatusSelected: (String?) -> Unit,
    onTypeSelected: (String?) -> Unit
) {
    // Definir una altura mínima para la sección de filtros
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(IntrinsicSize.Max) // Definir altura
    ) {
        // Menú desplegable para filtrar por `status`
        DropdownFilterMenu(
            label = "Filtrar por Estado",
            options = listOf("open", "close", "won"),
            selectedOption = selectedStatus,
            onOptionSelected = onStatusSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Menú desplegable para filtrar por `type`
        DropdownFilterMenu(
            label = "Filtrar por Tipo",
            options = listOf("simple", "system"),
            selectedOption = selectedType,
            onOptionSelected = onTypeSelected
        )
    }
}


@Composable
fun DropdownFilterMenu(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = BlackPrimary,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = selectedOption ?: "Seleccionar",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        text = { Text(text = option) }
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(null) // Limpiar selección con cadena vacía
                        expanded = false
                    },
                    text = { Text(text = "Limpiar selección") }
                )
            }
        }
    }
}