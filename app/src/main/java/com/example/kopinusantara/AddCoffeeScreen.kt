package com.example.kopinusantara

import androidx.compose.foundation.horizontalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCoffeeScreen(vm: CoffeeViewModel, onDone: () -> Unit) {
    // rememberSaveable: isi form tidak hilang saat layar diputar
    var name by rememberSaveable { mutableStateOf("") }
    var origin by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var roast by rememberSaveable { mutableStateOf("Medium") }
    var flavors by rememberSaveable { mutableStateOf("") }
    var tips by rememberSaveable { mutableStateOf("") }

    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var originError by rememberSaveable { mutableStateOf<String?>(null) }
    var flavorError by rememberSaveable { mutableStateOf<String?>(null) }
    var formError by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Kopi") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it; nameError = null },
                label = { Text("Nama kopi *") },
                isError = nameError != null,
                supportingText = { nameError?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Daerah asal *", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                ORIGINS.forEach { o ->
                    FilterChip(
                        selected = origin == o,
                        onClick = { origin = o; originError = null },
                        label = { Text(o) }
                    )
                }
            }
            originError?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Lokasi kebun (opsional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Tingkat sangrai", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ROASTS.forEach { r ->
                    FilterChip(selected = roast == r, onClick = { roast = r }, label = { Text(r) })
                }
            }

            OutlinedTextField(
                value = flavors,
                onValueChange = { flavors = it; flavorError = null },
                label = { Text("Notes rasa (pisahkan dengan koma)") },
                isError = flavorError != null,
                supportingText = { Text(flavorError ?: "Contoh: Floral, Rempah, Jeruk") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tips,
                onValueChange = { tips = it },
                label = { Text("Tips seduh (opsional)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            if (formError) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Periksa kembali formulir. Lengkapi kolom yang wajib diisi.",
                        Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Button(
                onClick = {
                    // Validasi input
                    nameError = if (name.trim().length < 3) "Nama kopi minimal 3 karakter" else null
                    originError = if (origin.isBlank()) "Pilih daerah asal terlebih dahulu" else null
                    flavorError = if (flavors.isBlank()) "Notes rasa tidak boleh kosong" else null
                    formError = nameError != null || originError != null || flavorError != null

                    if (!formError) {
                        vm.addCoffee(name, origin, location, roast, flavors, tips)
                        onDone()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) { Text("Simpan") }
        }
    }
}
