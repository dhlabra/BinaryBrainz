package com.example.binarybrainz.Navigation

import AsignarRolesScreen
import MenuAsesoriasPendientesScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Abogados.CitasPendientesScreen
import com.example.binarybrainz.Abogados.HistorialScreen
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.StudentViews.EditarCasosAbogadosView
import com.example.binarybrainz.StudentViews.EditarCasosEstudiantesView

@Composable
fun AbogadoNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "menu_casos_pendientes_view") {
        composable("menu_casos_pendientes_view") {
            MenuAsesoriasPendientesScreen(navController, viewModel)
        }
        composable("menu_historial_view") {
            HistorialScreen(navController, viewModel)
        }
        composable("edit_case_view/{caseId}") { backStackEntry ->
            val caseId = backStackEntry.arguments?.getString("caseId") ?: "N/A"
            EditarCasosEstudiantesView(navController, caseId)
        }
        composable("asignar_roles") {
            AsignarRolesScreen(navController, viewModel)
        }
        composable("citas_pendientes") {
            CitasPendientesScreen(navController, viewModel)
        }
        composable("editar_casos_abogados/{caseId}") { backStackEntry ->
            val caseId = backStackEntry.arguments?.getString("caseId")?.toIntOrNull() ?: -1
            EditarCasosAbogadosView(navController, caseId, viewModel)
        }
    }
}
