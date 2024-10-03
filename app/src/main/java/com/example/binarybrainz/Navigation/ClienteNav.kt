package com.example.binarybrainz.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Clientes.VistaServicios
import com.example.binarybrainz.Extras.UserViewModel

@Composable
fun ClienteNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "vista_servicios") {
        composable("vista_servicios") {
            VistaServicios(navController, viewModel)
        }
    }
}