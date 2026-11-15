package com.example.kopinusantara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kopinusantara.ui.theme.*
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
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = CoffeeBrown,
                contentColor = Color.White,
                shape = RoundedCornerShape(18.dp)
            ) { Icon(Icons.Default.Add, contentDescription = "Tambah kopi") }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {

            // ---------- Header gradasi + pencarian ----------
            Column(
                Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(CoffeeGradient)
                    .statusBarsPadding()
                    .padding(start = 20.dp, end = 12.dp, top = 12.dp, bottom = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Selamat datang,", style = MaterialTheme.typography.bodySmall, color = Cream.copy(alpha = 0.8f))
                        Text(vm.username.ifBlank { "Pengguna" }, style = MaterialTheme.typography.headlineSmall, color = Cream)
                    }
                    IconButton(onClick = onFavoritesClick) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorit", tint = Caramel)
                    }
                    // Sync + penanganan koneksi internet
                    TextButton(onClick = {
                        scope.launch {
                            if (isOnline(context))
                                snackbarHostState.showSnackbar("Sinkronisasi berhasil (data dummy)")
                            else
                                snackbarHostState.showSnackbar("Tidak ada koneksi internet. Coba lagi nanti.")
                        }
                    }) { Text("Sync", color = Cream) }
                    TextButton(onClick = onLogout) { Text("Keluar", color = Cream) }
                }
                Spacer(Modifier.height(12.dp))
                // Fitur: pencarian
                OutlinedTextField(
                    value = vm.query,
                    onValueChange = { vm.query = it },
                    placeholder = { Text("Cari nama kopi / daerah") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = coffeeFieldColors(),
                    modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
                )
            }

            Column(Modifier.padding(horizontal = 16.dp)) {
                Spacer(Modifier.height(12.dp))
                // Fitur: filter daerah
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf("Semua") + ORIGINS) { origin ->
                        FilterChip(
                            selected = vm.selectedOrigin == origin,
                            onClick = { vm.selectedOrigin = origin },
                            label = { Text(origin) },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CoffeeBrown,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "${list.size} kopi ditemukan",
                    style = MaterialTheme.typography.labelMedium,
                    color = Espresso.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                if (list.isEmpty()) {
                    EmptyState("Kopi tidak ditemukan", "Coba kata kunci atau daerah lain")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
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
}
