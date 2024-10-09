import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorariosDisponiblesView(navController: NavController, viewModel: UserViewModel) {
    val scope = rememberCoroutineScope()
    var fechaSeleccionada by remember { mutableStateOf<Long?>(null) }
    var resultado by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    // TimePicker state to store selected time
    val currentTime = java.util.Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(java.util.Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(java.util.Calendar.MINUTE),
        is24Hour = true
    )

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

            // Botón para abrir el selector de fecha modal
            Button(onClick = { showDatePicker = true }) {
                Text("Seleccionar fecha")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar la fecha seleccionada
            Text(
                text = fechaSeleccionada?.let {
                    java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(it)
                } ?: "No se ha seleccionado ninguna fecha"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TimePicker para seleccionar la hora
            TimeInput(
                state = timePickerState,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para mandar la solicitud
            Button(onClick = {
                scope.launch {
                    if (fechaSeleccionada != null) {
                        resultado = "Solicitud enviada para la fecha: ${java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(fechaSeleccionada)} a las ${timePickerState.hour}:${timePickerState.minute}"
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

    // Mostrar el DatePickerModal cuando se presione el botón de "Seleccionar fecha"
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { fecha ->
                fechaSeleccionada = fecha
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
