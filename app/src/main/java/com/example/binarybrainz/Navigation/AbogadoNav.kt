package com.example.binarybrainz.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Abogados.HistorialScreen
import com.example.binarybrainz.Abogados.MenuCasosPendientesScreen
import com.example.binarybrainz.Abogados.MenuPlantillas
import com.example.binarybrainz.Abogados.SignUpView
import com.example.binarybrainz.Extras.UserViewModel

@Composable
fun AbogadoNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "menu_casos_pendientes_view") {
        composable("menu_casos_pendientes_view") {
            MenuCasosPendientesScreen(navController, viewModel)
        }
        composable("creacion_caso_view") {
            MenuPlantillas(navController, viewModel)
        }
        composable("menu_historial_view") {
            HistorialScreen(navController, viewModel)
        }
    }
}