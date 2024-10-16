package com.example.binarybrainz.Navigation

import GenerarCasosClientesView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.binarybrainz.ClientViews.CitaClienteUpdatesView
import com.example.binarybrainz.Clientes.VistaServicios
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.UserViews.MasInformacionView

@Composable
fun ClienteNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "vista_servicios") {
        composable("vista_servicios") {
            VistaServicios(navController, viewModel)
        }
        composable(
                "mas_informacion_view/{servicioDescription}",
                arguments = listOf(navArgument("servicioDescription") { type = NavType.StringType })
            ) { backStackEntry ->
                val servicioDescription = backStackEntry.arguments?.getString("servicioDescription")
                MasInformacionView(navController, servicioDescription)
        }
        composable("generar_casos_clientes_view") {
                GenerarCasosClientesView(navController, viewModel)
        }
        composable("cita_cliente_updates_view") {
            CitaClienteUpdatesView(navController, viewModel)
        }
    }
}