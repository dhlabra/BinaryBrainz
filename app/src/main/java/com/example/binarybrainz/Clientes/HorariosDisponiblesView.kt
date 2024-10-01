import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorariosDisponiblesView(navController: NavController, viewModel: UserViewModel) {
    val scope = rememberCoroutineScope()
    var fecha by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar Cita") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Agregando el CalendarView más centrado
            AndroidView(
                factory = { CalendarView(context) },
                update = { calendarView ->
                    calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        // Guardamos la fecha seleccionada
                        fecha = "$year-${month + 1}-$dayOfMonth"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                // Lógica para mandar la solicitud (puedes actualizar la lógica aquí según tu necesidad)
                scope.launch {
                    if (fecha.isNotEmpty()) {
                        resultado = "Solicitud enviada para la fecha: $fecha"
                        // Navegar hacia la siguiente pantalla si es necesario
                        navController.navigateUp() // Puedes cambiar esto para la vista siguiente
                    } else {
                        resultado = "Por favor, selecciona una fecha."
                    }
                }
            }) {
                Text("Mandar Solicitud")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar resultado
            Text(resultado)
        }
    }
}
