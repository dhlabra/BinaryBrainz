package com.example.binarybrainz.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Abogados.HistorialScreen
import com.example.binarybrainz.Abogados.MenuCasosPendientesScreen
import com.example.binarybrainz.Abogados.MenuPlantillas
import com.example.binarybrainz.Abogados.SignUpView
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.StudentViews.ApartadoCasosCompartidosView

@Composable
fun EstudianteNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "apartado_casos_compartidos_view") {
        composable("apartado_casos_compartidos_view") {
            ApartadoCasosCompartidosView(navController, viewModel)
        }
    }
}