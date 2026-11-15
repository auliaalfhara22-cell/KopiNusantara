package com.example.kopinusantara

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    vm: CoffeeViewModel,
    onCoffeeClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val list = vm.filteredCoffees

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Halo, ${vm.username}") },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorit")
                    }
                    // Sync + penanganan koneksi internet
                    TextButton(onClick = {
                        scope.launch {
                            if (isOnline(context))
                                snackbarHostState.showSnackbar("Sinkronisasi berhasil (data dummy)")
                            else
                                snackbarHostState.showSnackbar("Tidak ada koneksi internet. Coba lagi nanti.")
                        }
                    }) { Text("Sync") }
                    TextButton(onClick = onLogout) { Text("Keluar") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah kopi")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(horizontal = 16.dp)) {
            // Fitur 1: pencarian
            OutlinedTextField(
                value = vm.query,
                onValueChange = { vm.query = it },
                label = { Text("Cari nama kopi / daerah") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // Fitur 2: filter daerah
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf("Semua") + ORIGINS) { origin ->
                    FilterChip(
                        selected = vm.selectedOrigin == origin,
                        onClick = { vm.selectedOrigin = origin },
                        label = { Text(origin) }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            if (list.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Kopi tidak ditemukan", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(list, key = { it.id }) { coffee ->
                        CoffeeCard(
                            coffee = coffee,
                            onClick = { onCoffeeClick(coffee.id) },
                            onFavorite = { vm.toggleFavorite(coffee.id) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeCard(coffee: Coffee, onClick: () -> Unit, onFavorite: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(coffee.name, style = MaterialTheme.typography.titleMedium)
                Text(coffee.location, style = MaterialTheme.typography.bodyMedium)
                Text("${coffee.roast} • ${coffee.brewCount}x seduh", style = MaterialTheme.typography.bodySmall)
                Text("Notes: ${coffee.flavors}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    if (coffee.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorit"
                )
            }
        }
    }
}
