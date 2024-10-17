package com.example.binarybrainz.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.StudentViews.ApartadoCasosCompartidosView
import com.example.binarybrainz.StudentViews.EditarCasosEstudiantesView

@Composable
fun EstudianteNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "apartado_casos_compartidos_view") {
        composable("apartado_casos_compartidos_view") {
            ApartadoCasosCompartidosView(navController, viewModel)
        }
        composable("edit_case_view/{caseId}") { backStackEntry ->
            val caseId = backStackEntry.arguments?.getString("caseId")?.toIntOrNull() ?: -1
            EditarCasosEstudiantesView(navController, caseId, viewModel)
        }
    }
}