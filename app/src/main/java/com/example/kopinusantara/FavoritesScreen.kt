package com.example.kopinusantara

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(vm: CoffeeViewModel, onCoffeeClick: (Int) -> Unit, onBack: () -> Unit) {
    val favs = vm.favoriteCoffees
    Scaffold(topBar = { CoffeeTopBar("Kopi Favorit", onBack) }) { padding ->
        if (favs.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize()) {
                EmptyState("Belum ada kopi favorit", "Tekan ikon hati pada kopi untuk menambahkan")
            }
        } else {
            LazyColumn(
                Modifier.padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(favs, key = { it.id }) { coffee ->
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
