package com.example.kopinusantara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel dipakai bersama semua halaman (scope Activity).
 * Data tetap aman saat layar diputar dan saat pindah halaman.
 */
class CoffeeViewModel : ViewModel() {

    // ---------- STATE ----------
    var isLoggedIn by mutableStateOf(false)
        private set
    var username by mutableStateOf("")
        private set

    var query by mutableStateOf("")
    var selectedOrigin by mutableStateOf("Semua")

    // Dummy data
    val coffees = mutableStateListOf(
        Coffee(1, "Gayo Aceh", "Aceh", "Takengon, Aceh Tengah", "Dark", "Dark Chocolate, Earthy, Spices", "V60, rasio 1:15, suhu 92°C", 24, true),
        Coffee(2, "Toraja Sapan", "Sulawesi", "Toraja Utara, Sulawesi", "Medium", "Citrus, Caramel, Herbal", "French press, 4 menit", 18, true),
        Coffee(3, "Kintamani Bali", "Bali", "Kintamani, Bali", "Light", "Orange Tangerine, Floral", "Pour over, air 90°C", 12),
        Coffee(4, "Flores Bajawa", "Flores", "Ngada, NTT", "Medium", "Nutty, Sweet Tobacco, Cocoa", "Aeropress, 2 menit", 9, true),
        Coffee(5, "Java Preanger", "Jawa", "Pangalengan, Jawa Barat", "Light", "Black Tea, Jasmine, Peach", "V60, gilingan sedang", 15)
    )
    private var nextId = 6

    // ---------- DERIVED ----------
    /** Hasil pencarian + filter daerah */
    val filteredCoffees: List<Coffee>
        get() = coffees.filter {
            (selectedOrigin == "Semua" || it.origin == selectedOrigin) &&
                (query.isBlank() ||
                    it.name.contains(query.trim(), ignoreCase = true) ||
                    it.location.contains(query.trim(), ignoreCase = true))
        }

    val favoriteCoffees: List<Coffee>
        get() = coffees.filter { it.isFavorite }

    // ---------- AKSI ----------
    /** Return null jika sukses, atau pesan error jika gagal. */
    fun login(user: String, pass: String): String? {
        if (user.isBlank() || pass.isBlank()) return "Username dan password tidak boleh kosong"
        if (user.trim() != "admin" || pass != "123456")
            return "Kata sandi salah, silakan memasukkan ulang kata sandi" n
        isLoggedIn = true
        username = user.trim()
        return null
    }

    fun logout() {
        isLoggedIn = false
        username = ""
    }

    fun getCoffee(id: Int): Coffee? = coffees.firstOrNull { it.id == id }

    fun addCoffee(name: String, origin: String, location: String, roast: String, flavors: String, tips: String) {
        val loc = if (location.isBlank()) origin else location.trim()
        coffees.add(
            Coffee(nextId++, name.trim(), origin, loc, roast, flavors.trim(),
                if (tips.isBlank()) "-" else tips.trim())
        )
    }

    fun toggleFavorite(id: Int) = update(id) { it.copy(isFavorite = !it.isFavorite) }

    fun brew(id: Int) = update(id) { it.copy(brewCount = it.brewCount + 1) }

    fun delete(id: Int) {
        coffees.removeAll { it.id == id }
    }

    private fun update(id: Int, change: (Coffee) -> Coffee) {
        val index = coffees.indexOfFirst { it.id == id }
        if (index >= 0) coffees[index] = change(coffees[index])
    }
}
