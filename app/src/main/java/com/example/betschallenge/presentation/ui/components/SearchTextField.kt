package com.example.betschallenge.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchTextField(
    searchQuery: TextFieldValue,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    debounceTime: Long = 500L // Tiempo de espera en milisegundos antes de ejecutar la búsqueda
) {
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    var text by remember { mutableStateOf(searchQuery.text) }
    val coroutineScope = rememberCoroutineScope()
    TextField(
        value = searchQuery,
        onValueChange = { newText ->
            text = newText.text
            debounceJob?.cancel() // Cancelar el trabajo anterior si el usuario sigue escribiendo
            debounceJob = coroutineScope.launch {
                delay(debounceTime) // Esperar un tiempo específico antes de ejecutar la búsqueda
                onSearchTextChanged(text) // Ejecutar la búsqueda solo cuando el usuario termine de escribir
            }
        },
        placeholder = { Text(text = "Buscar...") },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
        )
    )
}