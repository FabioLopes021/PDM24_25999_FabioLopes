package com.example.lojasocial.navigation
import kotlinx.serialization.Serializable


@Serializable
sealed class Route{
    @Serializable
    data object Loading: Route()
    @Serializable
    data object Home: Route()
//    data class Home(
//        var email: String?
//    ): Route()
    @Serializable
    data object Login: Route()
    @Serializable
    data object Signup: Route()
}