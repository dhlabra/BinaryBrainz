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
                UserAuthScreen(
                    UserViewModel(
                        UserRepository(
                            supabase,
                            CoroutineScope(Dispatchers.IO)
                        )
                    )
                )
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
                    "empty" -> SignUpView2(navController, viewModel)
                    else -> LoadingScreen()
                }
            }

            SessionStatus.LoadingFromStorage -> LoadingScreen()
            SessionStatus.NetworkError -> ErrorScreen("Network error")
            is SessionStatus.NotAuthenticated -> LoginInject(navController, viewModel)
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
}
