package com.example.binarybrainz.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Abogados.SignUpView
import com.example.binarybrainz.Extras.LoginView
import com.example.binarybrainz.Extras.UserViewModel

@Composable
fun GeneralNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "login_view") {
        composable("login_view") {
            LoginView(navController, viewModel)
        }
        composable("signup_admin_view") {
            SignUpView(navController, viewModel)
        }
    }
}