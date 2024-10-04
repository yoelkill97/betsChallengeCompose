package com.example.betschallenge.presentation.ui.screens.bets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betschallenge.R
import com.example.betschallenge.domain.entity.Resource
import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.betschallenge.domain.entity.bets_detail.BetSelectionEntity
import com.example.betschallenge.presentation.ui.components.LoadingScreen

@Preview(showBackground = true)
@Composable
fun BetsDetailPreview() {
    BetsDetailScreen()
}
@Composable
fun BetsDetailPage(betsViewModel: BetsViewModel = hiltViewModel(), betId: String , onBackPressed : () -> Unit = {}) {
    val betsDetailUIState = betsViewModel.betDetailState.collectAsState()
    LaunchedEffect(key1 = betId) {
        betsViewModel.getBetDetailByFilter(betId)
    }
    BetsDetailScreen(betsDetailUIState.value, onRetry = { betsViewModel.getBetDetailByFilter(betId) }, onBackPressed = onBackPressed ,detailGame = betId)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetsDetailScreen(uiBetsDetailState: Resource<List<BetDetailEntity>> = Resource.Idle, onRetry: () -> Unit = {}, onBackPressed : () -> Unit = {},detailGame:String = "") {
    Scaffold (
        modifier = Modifier.fillMaxWidth(),
        contentColor = Color.White,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    navigationIconContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = if (detailGame.isEmpty()) {
                            "Detalle"
                        } else {
                            "Game $detailGame"
                        },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    )
                },
            )
        }
    ){ innerPadding ->
        when (uiBetsDetailState) {
            is Resource.Idle ,is Resource.Loading -> {
                // Estado inicial, mostrar un mensaje o imagen de carga
                Text(text = "Esperando detalles...")
            }
            is Resource.Success -> {
                // Mostrar la lista de detalles en un LazyColumn
                if(uiBetsDetailState.data.isEmpty()) EmptyBetDetail(onRetry)
                else
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
                    items( uiBetsDetailState.data) { detail ->
                        BetDetailItem(detail)
                    }
                }
            }
            is Resource.Error -> {
                // Mostrar un mensaje de error con la opción de volver a intentar
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "Error: ${uiBetsDetailState.message}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onRetry() }) {
                        Text(text = "Volver a intentar")
                    }
                }
            }
        }
        LoadingScreen(isShowingDialog = uiBetsDetailState is Resource.Loading)
    }
}

@Composable
fun EmptyBetDetail(onRetry: () -> Unit) {
    // Lista vacía, mostrar mensaje y botón de refrescar
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the available space
            .padding(16.dp), // Addpadding if needed
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "No hay detalles de apuestas disponibles.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onRetry() }) {
            Text(text = "Refrescar")
        }
    }
}
@Composable
fun BetDetailItem(detail: BetDetailEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Sección de encabezado
            Text(
                text = "Detalles de la Apuesta",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Sección de información general de la apuesta
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Nivel: ${detail.betNivel}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(text = "Estado: ${detail.betStatusName}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Tipo: ${detail.betTypeName}", fontSize = 14.sp)
                Text(text = "Monto: ${detail.totalStake}", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Ganancia Total: ${detail.totalWin}", fontSize = 14.sp, color = Color.Green)
                Text(text = "Fecha: ${detail.createdDate}", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Sección desplegable de `betSelections`
            ExpandableSection(
                title = "Detalles del Evento (${detail.betSelections.size})",
                content = {
                    Column {
                        detail.betSelections.forEach { selection ->
                            BetSelectionItem(selection)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BetSelectionItem(selection: BetSelectionEntity) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Evento: ${selection.eventName}", fontWeight = FontWeight.Bold)
        Text(text = "Precio: ${selection.price}")
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Cabecera del componente desplegable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Contraer" else "Expandir"
            )
        }

        // Contenido del componente desplegable con animación
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            content()
        }
    }
}