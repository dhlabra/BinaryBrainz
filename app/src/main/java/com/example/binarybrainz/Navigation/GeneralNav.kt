package com.example.binarybrainz.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.binarybrainz.Abogados.HistorialScreen
import com.example.binarybrainz.Abogados.MenuCasosPendientesScreen
import com.example.binarybrainz.Abogados.SignUpView
import com.example.binarybrainz.Abogados.SignUpView2
import com.example.binarybrainz.Clientes.ClienteView
import com.example.binarybrainz.Clientes.VistaServicios
import com.example.binarybrainz.Extras.LoginView
import com.example.binarybrainz.Extras.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GeneralNavigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "login_view") {
        composable("login_view") {
            LoginView(navController, viewModel)
        }
        composable("signup_admin_view") {
            SignUpView(navController, viewModel)
        }
        composable("signup_admin_view_2") {
            SignUpView2(navController, viewModel)
        }
        composable("cliente_view") {
            ClienteView(navController, viewModel)
        }
    }
}