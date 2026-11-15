package com.example.kopinusantara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.kopinusantara.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)   // tampil 2 detik
        onFinish()
    }
    Box(Modifier.fillMaxSize().background(CoffeeGradient)) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.size(130.dp).clip(CircleShape).background(Cream.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) { CoffeeCupIcon(Modifier.size(80.dp), Cream) }
            Spacer(Modifier.height(20.dp))
            Text("Kopi Nusantara", style = MaterialTheme.typography.headlineLarge, color = Cream)
            Text(
                "Jelajahi kopi dari seluruh negeri",
                style = MaterialTheme.typography.bodyMedium,
                color = Cream.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Gayo  •  Toraja  •  Kintamani  •  Bajawa",
                style = MaterialTheme.typography.labelMedium,
                color = Caramel
            )
            Spacer(Modifier.height(32.dp))
            CircularProgressIndicator(color = Caramel, strokeWidth = 3.dp, modifier = Modifier.size(28.dp))
        }
        Text(
            "v1.0.0",
            Modifier.align(Alignment.BottomCenter).navigationBarsPadding().padding(bottom = 24.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Cream.copy(alpha = 0.6f)
        )
    }
}
