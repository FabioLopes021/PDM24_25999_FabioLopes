package com.example.firebase_room.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebase_room.ui.presentation.member_details.MemberListScreen
import com.example.firebase_room.ui.presentation.member_list.MemberDetailsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "member_list") {
        composable("member_list") {
            MemberListScreen(navController)
        }
        composable("member_details") {
            MemberDetailsScreen()
        }
    }
}