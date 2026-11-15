package com.example.kopinusantara

data class Coffee(
    val id: Int,
    val name: String,
    val origin: String,        // Aceh, Sulawesi, ...
    val location: String,      // Takengon, Aceh Tengah
    val roast: String,         // Light / Medium / Dark
    val flavors: String,       // "Dark Chocolate, Earthy, Spices"
    val brewTips: String,
    val brewCount: Int = 0,
    val isFavorite: Boolean = false
)

val ORIGINS = listOf("Aceh", "Sulawesi", "Bali", "Flores", "Jawa")
val ROASTS = listOf("Light", "Medium", "Dark")
