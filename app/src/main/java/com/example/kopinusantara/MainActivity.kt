package com.example.kopinusantara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kopinusantara.ui.theme.KopiNusantaraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KopiNusantaraTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // ViewModel milik Activity -> data selamat dari rotasi layar & pindah halaman
    val vm: CoffeeViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(onFinish = {
                val next = if (vm.isLoggedIn) "dashboard" else "login"
                navController.navigate(next) { popUpTo("splash") { inclusive = true } }
            })
        }
        composable("login") {
            LoginScreen(vm) {
                navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
            }
        }
        composable("dashboard") {
            DashboardScreen(
                vm = vm,
                onCoffeeClick = { navController.navigate("detail/$it") },
                onAddClick = { navController.navigate("add") },
                onFavoritesClick = { navController.navigate("favorites") },
                onLogout = {
                    vm.logout()
                    navController.navigate("login") { popUpTo("dashboard") { inclusive = true } }
                }
            )
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            DetailScreen(vm, entry.arguments?.getInt("id") ?: -1) { navController.popBackStack() }
        }
        composable("add") {
            AddCoffeeScreen(vm) { navController.popBackStack() }
        }
        composable("favorites") {
            FavoritesScreen(
                vm = vm,
                onCoffeeClick = { navController.navigate("detail/$it") },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
