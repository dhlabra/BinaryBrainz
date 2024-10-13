// Prueba1
package com.example.binarybrainz

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.Abogados.AbogadoView
import com.example.binarybrainz.Abogados.SignUpView2
import com.example.binarybrainz.Clientes.ClienteView
import com.example.binarybrainz.Clientes.VistaServicios
import com.example.binarybrainz.Extras.LoginView
import com.example.binarybrainz.Extras.UserRepository
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme
import com.example.binarybrainz.Estudiantes.EstudianteView
import com.example.binarybrainz.Extras.LoginInject
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://ptmutavwmroqlnzxblzg.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB0bXV0YXZ3bXJvcWxuenhibHpnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjYzNTAyMzgsImV4cCI6MjA0MTkyNjIzOH0.pOjkUeB5okRMH1UV7kc-1f2LUZevN40kjKiLvqCSq1I"
    ) {
        install(Auth)
        install(Postgrest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BinaryBrainzTheme {
                UserAuthScreen(UserViewModel(UserRepository(supabase, CoroutineScope(Dispatchers.IO))))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun UserAuthScreen(viewModel: UserViewModel) {
        val navController = rememberNavController()
        val sessionState by viewModel.sessionState.collectAsState()
        val userRole = viewModel.userRole.value

        when (sessionState) {
            is SessionStatus.Authenticated -> {
                when (userRole) {
                    "abogado" -> AbogadoView(navController, viewModel)
                    "estudiante" -> EstudianteView(navController, viewModel)
                    "cliente" -> ClienteView(navController, viewModel)
                    else -> SignUpView2(navController, viewModel)
                }
            }
            SessionStatus.LoadingFromStorage -> LoadingScreen()
            SessionStatus.NetworkError -> ErrorScreen("Network error")
            is SessionStatus.NotAuthenticated -> LoginInject(navController, viewModel)
        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    @Composable
//    fun AppNavigation(viewModel: UserViewModel) {
//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = "vista_servicios") {
//            composable("vista_servicios") {
//                VistaServicios(navController)
//            }
//            composable("login_view") {
//                LoginView(navController, viewModel)
//            }
//            composable("signup_view") {
//                SignUpView(navController, viewModel)
//            }
//            composable("generar_casos_clientes_view") { // Añade la navegación para GenerarCasosClientesView
//                GenerarCasosClientesView(navController)
//            }
//            composable("apartado_casos_compartidos_view") {
//                ApartadoCasosCompartidosView(navController, viewModel)
//            }
//            composable("edit_case_view/{caseId}") { backStackEntry ->
//                val caseId = backStackEntry.arguments?.getString("caseId") ?: "N/A"
//                EditarCasosEstudiantesView(navController, caseId)
//            }
//            composable(
//                "mas_informacion_view/{servicioDescription}",
//                arguments = listOf(navArgument("servicioDescription") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val servicioDescription = backStackEntry.arguments?.getString("servicioDescription")
//                MasInformacionView(navController, servicioDescription)
//            }
//            composable("menu_historial_view") {
//                HistorialScreen(navController, viewModel)
//            }
//            composable("menu_casos_pendientes_view") {
//                MenuCasosPendientesScreen(navController, viewModel)
//            }
//            composable("creacion_caso_view") {
//                MenuPlantillas(navController, viewModel)
//            }
//            composable("horarios_disponibles_view") {
//                HorariosDisponiblesView(navController, viewModel)
//            }
//        }
//    }
}


@Composable
fun ImageCardVertical(imageId: Int, description: String, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(16.dp)
            .safeDrawingPadding(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = description,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = description
                )
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = onClick
                ) {
                    Text(text = "Más información")
                }
            }
        }
    }
}

@Composable
fun ImageCardHorizontal(imageId: Int, description: String, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(12.dp)
            .padding(vertical = 16.dp)
            .width(300.dp)
            .safeDrawingPadding(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = description,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = description
                )
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = onClick
                ) {
                    Text(text = "Crear")
                }
            }
        }
    }
}



@Composable
fun ErrorScreen(message: String) {
    Text(message)
}


@Composable
fun LoadingScreen() {
    CircularProgressIndicator()
}

//@Preview(showBackground = true)
//@Composable
//fun VistaServiciosPreview() {
//    BinaryBrainzTheme {
//        VistaServicios(navController = rememberNavController())
//    }
//}
