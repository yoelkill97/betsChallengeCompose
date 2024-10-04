package com.example.betschallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.betschallenge.presentation.ui.screens.SplashScreen
import com.example.betschallenge.presentation.ui.screens.bets.BetsDetailPage
import com.example.betschallenge.presentation.ui.screens.bets.BetsScreenPage
import com.example.betschallenge.presentation.ui.screens.login.LoginPage
import com.example.betschallenge.presentation.ui.screens.main.MainPage
import com.example.betschallenge.presentation.ui.screens.register.RegisterPage

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(Screens.MainScreen.route) {
                        popUpTo(it.destination.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Screens.MainScreen.route) {
            MainPage(
                onLogin = {
                    navController.navigate(Screens.LoginScreen.route){
                        launchSingleTop = true
                    }
                },
                onRegister = {
                    navController.navigate(Screens.RegisterScreen.route){
                        launchSingleTop = true
                    }
                }
            )

        }
        composable(route = Screens.LoginScreen.route) {
            LoginPage(
                loginSuccess = {
                    // Navegar a BetsScreen y limpiar la pila
                    navController.navigate(Screens.BetsScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true // Eliminar todas las pantallas anteriores
                        }
                        launchSingleTop = true // Evitar duplicados
                    }
                }
            )
        }
        composable(route = Screens.RegisterScreen.route) {
            RegisterPage(registerSuccess = {
                navController.navigate(Screens.BetsScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            })
        }
        composable(route = Screens.BetsScreen.route) {
            BetsScreenPage(
                onNavigateToBetDetail = { betId ->
                    navController.navigate(Screens.BetDetailScreen.createRoute(betId))
                }
            )
        }

        composable(
            route = Screens.BetDetailScreen.route,
            arguments = listOf(navArgument(NavArgs.betId.key) { type = NavType.StringType })
        ) { backStackEntry ->
            val betId = backStackEntry.arguments?.getString(NavArgs.betId.key) ?: ""
            BetsDetailPage(betId = betId, onBackPressed = { navController.navigateUp() })
        }

    }
}