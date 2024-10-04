package com.example.betschallenge.presentation.navigation

sealed class Screens(val route: String) {
    object SplashScreen : Screens(route = "splash_screen")
    object MainScreen : Screens(route = "main_screen")
    object LoginScreen : Screens(route = "login_screen")
    object RegisterScreen : Screens(route = "register_screen")
    object BetsScreen : Screens(route = "bets_screen")
    object BetDetailScreen : Screens(route = "bet_detail_screen/{${NavArgs.betId.key}}"){
        fun createRoute(betId: String) = "bet_detail_screen/$betId"
    }
}
