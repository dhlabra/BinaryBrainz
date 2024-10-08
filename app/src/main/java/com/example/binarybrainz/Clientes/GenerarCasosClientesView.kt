import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun <UserViewModel> GenerarCasosClientesView(navController: NavController, viewModel: UserViewModel) {
    var selectedCategory by remember { mutableStateOf("") }
    var caseDescription by remember { mutableStateOf(TextFieldValue("")) }
    var acceptTerms by remember { mutableStateOf(false) }
    var acceptStudents by remember { mutableStateOf(false) }
    var fechaSeleccionada by remember { mutableStateOf<Long?>(null) }
    var resultado by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val services = List(10) { "Servicio ${it + 1}" }
    val isFormComplete = selectedCategory.isNotBlank() && caseDescription.text.isNotBlank() && acceptTerms && acceptStudents

    // TimePicker state to store selected time
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitud de Ayuda", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ExposedDropdownMenuBox para seleccionar el servicio
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona Servicio") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor() // Para que el menú aparezca directamente bajo el campo
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    services.forEach { service ->
                        DropdownMenuItem(
                            text = { Text(service) },
                            onClick = {
                                selectedCategory = service
                                expanded = false
                            }
                        )
                    }
                }
            }

            // TextBox para descripción del caso (máximo 150 caracteres)
            OutlinedTextField(
                value = caseDescription,
                onValueChange = {
                    if (it.text.length <= 150) caseDescription = it
                },
                label = { Text("Breve Descripción", fontWeight = FontWeight.Medium) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4,
                placeholder = { Text("(150 caracteres max)", fontSize = 14.sp) }
            )

            // TimePicker para seleccionar la hora
            TimeInput(
                state = timePickerState,
            )

            // Botón para abrir el selector de fecha modal
            Button(onClick = { showDatePicker = true }) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Seleccionar Fecha")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Seleccionar Fecha")
            }

            // Mostrar la fecha seleccionada
            Text(
                text = fechaSeleccionada?.let {
                    java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                } ?: "No se ha seleccionado ninguna fecha"
            )

            // Check de términos y condiciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "Acepto los términos y condiciones.",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Check de aceptación de estudiantes en la cita
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptStudents,
                    onCheckedChange = { acceptStudents = it },
                    colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = "Acepto que alumnos estén presentes en la cita.",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón para mandar la solicitud
            Button(onClick = {
                scope.launch {
                    if (isFormComplete && fechaSeleccionada != null) {
                        resultado = "Solicitud enviada para el ${java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fechaSeleccionada)} a las ${timePickerState.hour}:${timePickerState.minute}"
                        // Aquí puedes mandar la información a tu base de datos
                        navController.navigateUp()
                    } else {
                        resultado = "Por favor, completa todos los espacios"
                    }
                }
            }) {
                Text("Mandar Solicitud")
            }

            // Mostrar resultado
            Text(resultado)
        }
    }

    // Mostrar el DatePickerModal cuando se presione el botón de "Seleccionar fecha"
    if (showDatePicker) {
        DatePickerModalGenerarCasos(
            onDateSelected = { fecha ->
                fechaSeleccionada = fecha
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

// Renombrar la función DatePickerModal para evitar conflictos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalGenerarCasos(
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
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GenerarCasosClientesViewPreview() {
    BinaryBrainzTheme {
        GenerarCasosClientesView(navController = rememberNavController(), viewModel = UserViewModel())
    }
}

fun UserViewModel() {

}
