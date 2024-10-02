import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorariosDisponiblesView(navController: NavController, viewModel: UserViewModel) {
    val scope = rememberCoroutineScope()
    var fecha by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    val horariosDisponibles = listOf("10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM")
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) } // Para controlar el Dropdown

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

            // CalendarView para seleccionar la fecha
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

            Spacer(modifier = Modifier.height(24.dp))

            // ExposedDropdownMenuBox para seleccionar el horario
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = horario,
                    onValueChange = { horario = it },
                    readOnly = true,
                    label = { Text("Selecciona Horario") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor() // Para que el menú aparezca directamente bajo el campo
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    horariosDisponibles.forEach { selectedTime ->
                        DropdownMenuItem(
                            text = { Text(selectedTime) },
                            onClick = {
                                horario = selectedTime
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                // Lógica para mandar la solicitud
                scope.launch {
                    if (fecha.isNotEmpty() && horario.isNotEmpty()) {
                        resultado = "Solicitud enviada para la fecha: $fecha a las $horario"
                        // Navegar hacia la siguiente pantalla si es necesario
                        navController.navigateUp()
                    } else {
                        resultado = "Por favor, selecciona una fecha y un horario."
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
