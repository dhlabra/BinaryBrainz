package com.example.binarybrainz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.binarybrainz.Abogados.HistorialScreen
import com.example.binarybrainz.Abogados.MenuCasosPendientesScreen
import com.example.binarybrainz.Abogados.MenuPlantillas
import com.example.binarybrainz.Extras.LoginView
import com.example.binarybrainz.Extras.SignUpView
import com.example.binarybrainz.StudentViews.ApartadoCasosCompartidosView
import com.example.binarybrainz.StudentViews.EditarCasosEstudiantesView
import com.example.binarybrainz.UserViews.MasInformacionView
import com.example.binarybrainz.UserViews.NecesitoAyudaView
import com.example.binarybrainz.UserViews.GenerarCasosClientesView // Importa la nueva vista aquí
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme
import com.example.binarybrainz.UserViews.VistaServicios
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://ptmutavwmroqlnzxblzg.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB0bXV0YXZ3bXJvcWxuenhibHpnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjYzNTAyMzgsImV4cCI6MjA0MTkyNjIzOH0.pOjkUeB5okRMH1UV7kc-1f2LUZevN40kjKiLvqCSq1I"
    ) {
        install(Auth)
        //install other modules
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            BinaryBrainzTheme {
                UserAuthScreen(UserViewModel(UserRepository(supabase, CoroutineScope(Dispatchers.IO))))
            }
        }
    }

    @Composable
    fun UserAuthScreen(viewModel: UserViewModel) {

        val sessionState by viewModel.sessionState.collectAsState()

        when (sessionState) {
            is SessionStatus.Authenticated -> MenuCasosPendientesScreen(rememberNavController(), viewModel)
            SessionStatus.LoadingFromStorage -> LoadingScreen()
            SessionStatus.NetworkError -> ErrorScreen("Network error")
            is SessionStatus.NotAuthenticated -> AppNavigation(viewModel)
        }
    }

    @Composable
    fun AppNavigation(viewModel: UserViewModel) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "vista_servicios") {
            composable("vista_servicios") {
                VistaServicios(navController)
            }
            composable("login_view") {
                LoginView(navController, viewModel)
            }
            composable("signup_view") {
                SignUpView(viewModel)
            }
            composable("necesito_ayuda_view") {
                NecesitoAyudaView()
            }
            composable("generar_casos_clientes_view") { // Añade la navegación para GenerarCasosClientesView
                GenerarCasosClientesView(navController)
            }
            composable("apartado_casos_compartidos_view") {
                ApartadoCasosCompartidosView(navController)
            }
            composable("edit_case_view/{caseId}") { backStackEntry ->
                val caseId = backStackEntry.arguments?.getString("caseId") ?: "N/A"
                EditarCasosEstudiantesView(navController, caseId)
            }
            composable(
                "mas_informacion_view/{servicioDescription}",
                arguments = listOf(navArgument("servicioDescription") { type = NavType.StringType })
            ) { backStackEntry ->
                val servicioDescription = backStackEntry.arguments?.getString("servicioDescription")
                MasInformacionView(navController, servicioDescription)
            }
            composable("menu_historial_view") {
                HistorialScreen(navController, viewModel)
            }
            composable("menu_casos_pendientes_view") {
                MenuCasosPendientesScreen(navController, viewModel)
            }
            composable("creacion_caso_view") {
                MenuPlantillas(navController, viewModel)
            }
        }
    }
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

@Preview(showBackground = true)
@Composable
fun VistaServiciosPreview() {
    BinaryBrainzTheme {
        VistaServicios(navController = rememberNavController())
    }
}
