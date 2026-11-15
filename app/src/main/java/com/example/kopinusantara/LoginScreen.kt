package com.example.kopinusantara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kopinusantara.ui.theme.*

@Composable
fun LoginScreen(vm: CoffeeViewModel, onLoginSuccess: () -> Unit) {
    // rememberSaveable: isi input tidak hilang saat layar diputar
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // Header gradasi
        Box(
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(CoffeeGradient)
                .statusBarsPadding()
                .padding(vertical = 36.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier.size(84.dp).clip(CircleShape).background(Cream.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) { CoffeeCupIcon(Modifier.size(52.dp), Cream) }
                Spacer(Modifier.height(12.dp))
                Text("Kopi Nusantara", style = MaterialTheme.typography.headlineMedium, color = Cream)
                Text(
                    "Katalog kopi dari seluruh negeri",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Cream.copy(alpha = 0.8f)
                )
            }
        }

        Column(Modifier.padding(24.dp)) {
            Text("Masuk", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Silakan masuk untuk mengelola katalog kopi",
                style = MaterialTheme.typography.bodyMedium,
                color = Espresso.copy(alpha = 0.65f)
            )
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it; error = null },
                label = { Text("Username") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = coffeeFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; error = null },
                label = { Text("Password") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = coffeeFieldColors(),
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        EyeIcon(Modifier.size(24.dp), visible = showPassword, color = CoffeeBrown)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Spacer(Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(it, Modifier.padding(12.dp), color = MaterialTheme.colorScheme.error)
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    error = vm.login(username, password)
                    if (error == null) onLoginSuccess()
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) { Text("Masuk", style = MaterialTheme.typography.titleMedium) }
        }
    }
}
