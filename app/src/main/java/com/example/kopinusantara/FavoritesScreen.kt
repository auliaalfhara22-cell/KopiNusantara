package com.example.kopinusantara

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(vm: CoffeeViewModel, onCoffeeClick: (Int) -> Unit, onBack: () -> Unit) {
    val favs = vm.favoriteCoffees
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kopi Favorit") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        if (favs.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Belum ada kopi favorit")
            }
        } else {
            LazyColumn(
                Modifier.padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
