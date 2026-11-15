package com.example.kopinusantara

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: CoffeeViewModel, id: Int, onBack: () -> Unit) {
    val coffee = vm.getCoffee(id)
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detail Kopi") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        if (coffee == null) {
            // Penanganan error: data tidak ditemukan
            Box(Modifier.padding(padding).fillMaxSize()) {
                Text("Data kopi tidak ditemukan.", Modifier.padding(24.dp))
            }
        } else {
            Column(
                Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(coffee.name, style = MaterialTheme.typography.headlineMedium)
                Text(coffee.location, style = MaterialTheme.typography.titleMedium)
                Text("Tingkat sangrai: ${coffee.roast}")

                Text("Notes rasa", style = MaterialTheme.typography.titleSmall)
                Text(coffee.flavors)

                Text("Tips seduh", style = MaterialTheme.typography.titleSmall)
                Text(coffee.brewTips)

                Text("Jumlah seduhan: ${coffee.brewCount} kali", style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(8.dp))
                // Fitur: catat seduhan (counter dinamis)
                Button(
                    onClick = {
                        vm.brew(coffee.id)
                        scope.launch { snackbarHostState.showSnackbar("Seduhan ${coffee.name} dicatat ☕") }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) { Text("Catat Seduhan") }

                OutlinedButton(
                    onClick = { vm.toggleFavorite(coffee.id) },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) { Text(if (coffee.isFavorite) "Hapus dari Favorit" else "Tambah ke Favorit") }

                OutlinedButton(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
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
