package com.example.kopinusantara

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kopinusantara.ui.theme.*

/** Gradasi cokelat dipakai di header, splash, dan login */
val CoffeeGradient = Brush.verticalGradient(listOf(Espresso, CoffeeBrown))

/** Warna sesuai tingkat sangrai */
fun roastColor(roast: String): Color = when (roast) {
    "Light" -> Color(0xFFE0A84F)
    "Dark" -> Color(0xFF5A3A26)
    else -> Caramel
}

/** Ikon cangkir kopi digambar sendiri (tanpa emoji) */
@Composable
fun CoffeeCupIcon(modifier: Modifier = Modifier, color: Color = Color.White) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val stroke = w * 0.07f
        // badan cangkir
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.18f, h * 0.38f),
            size = Size(w * 0.50f, h * 0.40f),
            cornerRadius = CornerRadius(w * 0.10f, w * 0.10f)
        )
        // gagang
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.58f, h * 0.46f),
            size = Size(w * 0.26f, h * 0.22f),
            style = Stroke(width = stroke)
        )
        // piring
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.10f, h * 0.82f),
            size = Size(w * 0.66f, h * 0.06f),
            cornerRadius = CornerRadius(w * 0.03f, w * 0.03f)
        )
        // uap
        for (x in listOf(0.30f, 0.43f, 0.56f)) {
            drawLine(
                color = color.copy(alpha = 0.8f),
                start = Offset(w * x, h * 0.12f),
                end = Offset(w * x, h * 0.28f),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun RoastBadge(roast: String) {
    Surface(shape = RoundedCornerShape(50), color = roastColor(roast).copy(alpha = 0.25f)) {
        Text(
            roast,
            Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Espresso
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeCard(coffee: Coffee, onClick: () -> Unit, onFavorite: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)).background(roastColor(coffee.roast)),
                contentAlignment = Alignment.Center
            ) {
                CoffeeCupIcon(Modifier.size(38.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(coffee.name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    coffee.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Espresso.copy(alpha = 0.65f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RoastBadge(coffee.roast)
                    Spacer(Modifier.width(8.dp))
                    Text("${coffee.brewCount}x seduh", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    "Notes: ${coffee.flavors}",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    if (coffee.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorit",
                    tint = if (coffee.isFavorite) Color(0xFFC62828) else Caramel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Espresso,
            titleContentColor = Cream,
            navigationIconContentColor = Cream
        )
    )
}

@Composable
fun coffeeFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    errorContainerColor = Color.White,
    focusedBorderColor = CoffeeBrown,
    unfocusedBorderColor = Latte
)

@Composable
fun EmptyState(title: String, subtitle: String) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.size(96.dp).clip(RoundedCornerShape(28.dp)).background(Latte),
            contentAlignment = Alignment.Center
        ) { CoffeeCupIcon(Modifier.size(60.dp), Caramel) }
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Espresso.copy(alpha = 0.65f))
    }
}

/** Ikon mata: terbuka = kata sandi terlihat, tercoret = kata sandi tersembunyi */
@Composable
fun EyeIcon(modifier: Modifier = Modifier, visible: Boolean, color: Color) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val stroke = w * 0.09f
        val eye = Path().apply {
            moveTo(w * 0.05f, h * 0.5f)
            quadraticBezierTo(w * 0.5f, h * 0.05f, w * 0.95f, h * 0.5f)
            quadraticBezierTo(w * 0.5f, h * 0.95f, w * 0.05f, h * 0.5f)
        }
        drawPath(eye, color = color, style = Stroke(width = stroke, cap = StrokeCap.Round))
        drawCircle(color = color, radius = w * 0.14f, center = Offset(w * 0.5f, h * 0.5f))
        if (!visible) {
            drawLine(
                color = color,
                start = Offset(w * 0.15f, h * 0.85f),
                end = Offset(w * 0.85f, h * 0.15f),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}
