package com.example.betschallenge.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(
    title: String = "Historial de Apuestas",
    onSearchTextChanged: (String) -> Unit,
    onCloseSearch: () -> Unit,
    debounceTime: Long = 1000L
) {
    // Estado para controlar la visibilidad del campo de búsqueda
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    // Control de corrutinas para el delay
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    TopAppBar(
        title = {
            // Cambiar el contenido del título según si la búsqueda está activa o no
            AnimatedVisibility(
                visible = !isSearchActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(text = title, fontSize = 20.sp, color = Color.White)
            }


            AnimatedVisibility(
                visible = isSearchActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { newText ->
                        searchQuery = newText

                        // Cancelar el trabajo anterior si hay uno activo
                        debounceJob?.cancel()

                        // Crear un nuevo trabajo de corrutina con delay
                        debounceJob = coroutineScope.launch {
                            delay(debounceTime) // Esperar antes de ejecutar la búsqueda
                            onSearchTextChanged(newText.text) // Llamar a la función de búsqueda solo si se dejó de escribir
                        }
                    },
                    placeholder = { Text(text = "Buscar...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors =OutlinedTextFieldDefaults.colors(
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
        },
        actions = {
            if (isSearchActive) {
                // Botón de cerrar búsqueda
                IconButton(onClick = {
                    isSearchActive = false
                    searchQuery = TextFieldValue("") // Limpiar el campo de búsqueda
                    debounceJob?.cancel() // Cancelar cualquier búsqueda en proceso
                    onCloseSearch() // Llamar a la función para cerrar búsqueda
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar búsqueda")
                }
            } else {
                // Botón de activar búsqueda
                IconButton(onClick = { isSearchActive = true }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}