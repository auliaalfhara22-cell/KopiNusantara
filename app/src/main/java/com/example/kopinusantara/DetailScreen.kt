package com.example.kopinusantara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kopinusantara.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(vm: CoffeeViewModel, id: Int, onBack: () -> Unit) {
    val coffee = vm.getCoffee(id)
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { CoffeeTopBar("Detail Kopi", onBack) }
    ) { padding ->
        if (coffee == null) {
            // Penanganan error: data tidak ditemukan
            EmptyState("Data kopi tidak ditemukan", "Kopi mungkin sudah dihapus")
        } else {
            Column(
                Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Hero
                Box(
                    Modifier.fillMaxWidth().height(170.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Brush.verticalGradient(listOf(roastColor(coffee.roast), Espresso))),
                    contentAlignment = Alignment.Center
                ) { CoffeeCupIcon(Modifier.size(96.dp), Cream) }

                Text(coffee.name, style = MaterialTheme.typography.headlineMedium)
                Text(coffee.location, style = MaterialTheme.typography.bodyLarge, color = Espresso.copy(alpha = 0.7f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RoastBadge(coffee.roast)
                    Spacer(Modifier.width(10.dp))
                    Text("Jumlah seduhan: ${coffee.brewCount} kali", style = MaterialTheme.typography.bodyMedium)
                }

                InfoCard("Notes rasa", coffee.flavors)
                InfoCard("Tips seduh", coffee.brewTips)

                Spacer(Modifier.height(4.dp))
                // Fitur: catat seduhan (counter dinamis)
                Button(
                    onClick = {
                        vm.brew(coffee.id)
                        scope.launch { snackbarHostState.showSnackbar("Seduhan ${coffee.name} dicatat") }
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) { Text("Catat Seduhan") }

                OutlinedButton(
                    onClick = { vm.toggleFavorite(coffee.id) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) { Text(if (coffee.isFavorite) "Hapus dari Favorit" else "Tambah ke Favorit") }

                OutlinedButton(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) { Text("Hapus Kopi") }
            }
        }
    }

    if (showDialog && coffee != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Hapus kopi?") },
            text = { Text("Apakah Anda yakin ingin menghapus \"${coffee.name}\" dari katalog? Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    vm.delete(coffee.id)
                    onBack()
                }) { Text("Hapus", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Batal") } }
        )
    }
}

@Composable
private fun InfoCard(title: String, body: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = CoffeeBrown)
            Spacer(Modifier.height(4.dp))
            Text(body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
