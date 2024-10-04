package com.example.betschallenge.presentation.ui.screens.bets

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betschallenge.domain.entity.Resource
import com.example.betschallenge.domain.entity.bets.BetItemEntity
import com.example.betschallenge.domain.entity.bets_detail.BetDetailEntity
import com.example.betschallenge.presentation.ui.components.FilterSection
import com.example.betschallenge.presentation.ui.components.LoadingScreen
import com.example.betschallenge.presentation.ui.components.SearchableTopBar
import com.example.betschallenge.presentation.ui.theme.CloseBetColor

import com.example.betschallenge.presentation.ui.theme.OpenDebtColor
import com.example.betschallenge.presentation.ui.theme.WiningColor
import com.example.betschallenge.presentation.ui.theme.WonBetColor
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun BetsScreenPreview() {
    BetsScreen()
}

@Composable
fun BetsScreenPage(betsViewModel: BetsViewModel = hiltViewModel(),onNavigateToBetDetail: (String) -> Unit = {}) {
    val betsUIState = betsViewModel.betsState.collectAsState()
    val filteredBetsUIState = betsViewModel.betDetailState.collectAsState()
    BetsScreen(filteredBetsUIState = filteredBetsUIState.value,betsUIState.value, isLoading = betsViewModel.isLoading.value ?: false, onRefreshClick = {
        betsViewModel.getBets()
    },onNavigateToBetDetail=onNavigateToBetDetail,onSearchTextChanged = { query ->betsViewModel.getBetDetailByFilter(query) },onCloseSearch = { betsViewModel.getBets() }, onFilterApplied = { status, type ->
        betsViewModel.filterBets(status, type)
    })
}


@Composable
fun BetsScreen(
    filteredBetsUIState: Resource<List<BetDetailEntity>> = Resource.Idle,
    betsUIState: Resource<List<BetItemEntity>> = Resource.Idle,
    isLoading: Boolean = false,
    onRefreshClick: () -> Unit = {},
    onNavigateToBetDetail: (String) -> Unit = {},
    onSearchTextChanged: (String) -> Unit = {},
    onCloseSearch: () -> Unit = {},
    onFilterApplied: (String?, String?) -> Unit ={ _, _ -> }
) {
    var isSearching by remember { mutableStateOf(false) }

    var selectedStatus by remember { mutableStateOf<String?>(null) }
    var selectedType by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            SearchableTopBar(
                title = "Historial de Apuestas",
                onSearchTextChanged = { query ->
                    isSearching = query.isNotEmpty()
                    onSearchTextChanged(query)
                },
                onCloseSearch = {
                    isSearching = false
                    onCloseSearch()
                }
            )
        },
        content = { paddingValues ->

            Box(modifier = Modifier.padding(paddingValues)) {
                // Mostrar la lista según el estado de `isSearching` y el estado de los recursos (`Resource`)
                when {
                    isSearching -> { // Mostrar la lista filtrada si estamos en modo de búsqueda
                        when (filteredBetsUIState) {
                            is Resource.Idle, is Resource.Loading -> {
                                Text(text = "Buscando apuestas...")
                            }
                            is Resource.Success -> {
                                if (filteredBetsUIState.data.isEmpty()) {
                                    Text(text = "No  se encontro resultado ")
                                } else {
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        contentPadding = PaddingValues(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    )  {
                                        items( filteredBetsUIState.data) { detail ->
                                            BetDetailItem(detail)
                                        }
                                    }
                                }
                            }
                            is Resource.Error -> {
                                val message = filteredBetsUIState.message
                                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else -> { // Mostrar el historial completo cuando no hay búsqueda activa
                        Column(modifier = Modifier.fillMaxWidth()) {
                            FilterSection(
                                selectedStatus = selectedStatus,
                                selectedType = selectedType,
                                onStatusSelected = { status ->
                                    selectedStatus = status
                                    onFilterApplied(status, selectedType) // Llamar a la función de filtro
                                },
                                onTypeSelected = { type ->
                                    selectedType = type
                                    onFilterApplied(selectedStatus, type) // Llamar a la función de filtro
                                }
                            )
                        when (betsUIState) {
                            is Resource.Idle, is Resource.Loading -> {
                                if (isLoading) Text(text = "Cargando historial...")
                            }

                            is Resource.Success -> {
                                if (betsUIState.data.isEmpty()) {
                                    EmptyBetDetail(onRetry = onRefreshClick) // Historial completo vacío
                                } else {
                                    BetList(
                                        bets = betsUIState.data,
                                        onNavigateToBetDetail
                                    ) // Mostrar historial completo
                                }
                            }

                            is Resource.Error -> {
                                val message = betsUIState.message
                                Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    }
                }

            }
            LoadingScreen(isShowingDialog =  filteredBetsUIState is Resource.Loading ||
                    betsUIState is Resource.Loading)
        }
    )
}

// Composable para mostrar la lista de apuestas
@Composable
fun BetList(bets: List<BetItemEntity>,onNavigateToBetDetail: (String) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(items = bets) { bet -> // Aquí se utiliza la sobrecarga adecuada de items
            BetItem(bet,onNavigateToBetDetail)
        }
    }
}


@Composable
fun BetItem(
    bet: BetItemEntity,
    onNavigateToBetDetail: (String) -> Unit = {}
) {

    val statusColor = when (bet.status.lowercase()) {
        "open" -> OpenDebtColor
        "close" -> CloseBetColor
        "won" -> WonBetColor
        else -> MaterialTheme.colorScheme.onSurface // Color predeterminado
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onNavigateToBetDetail(bet.game) // Navegar al detalle de la apuesta al hacer clic
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding( 16.dp)) {
            // Encabezado con información general de la apuesta
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Game: ${bet.game}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = bet.status.uppercase(), // Mostrar el estado en mayúsculas
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }

            // Detalles de la apuesta
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Monto Apostado: ${bet.wager}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Odds: ${bet.odds}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (bet.winning != null) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Ganancia: ${bet.winning}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = WiningColor
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onNavigateToBetDetail(bet.game) }) {
                    Text(
                        text = "Ver detalles",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}