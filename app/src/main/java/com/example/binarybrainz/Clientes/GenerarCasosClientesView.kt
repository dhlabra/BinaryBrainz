import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerarCasosClientesView(navController: NavController, viewModel: UserViewModel) {
    var selectedCategory by remember { mutableStateOf("") }
    var caseDescription by remember { mutableStateOf(TextFieldValue("")) }
    var acceptStudents by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val currentTime = Calendar.getInstance()
    val scope = rememberCoroutineScope()
    var expandedHour by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf("") }
    val availableHours = listOf("10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")
    var fechaSeleccionada by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showDateErrorDialog by remember { mutableStateOf(false) }

    val isFormComplete = selectedCategory.isNotBlank() && caseDescription.text.isNotBlank() && acceptStudents && selectedHour.isNotBlank()
    val services = listOf(
        "Consulta General",
        "Divorcio",
        "Herencias y Testamentos",
        "Accidentes de Tráfico",
        "Defensa Penal",
        "Custodia de Hijos",
        "Demandas Civiles",
        "Derecho Laboral",
        "Arrendamientos",
        "Mediación Familiar"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitud de Ayuda", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Agregar scroll vertical
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
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    services.forEachIndexed { index, service ->
                        DropdownMenuItem(
                            text = { Text(service) },
                            onClick = {
                                selectedCategory = service
                                expanded = false
                            }
                        )
                        if (index < services.size - 1) {
                            Divider()
                        }
                    }
                }
            }

            OutlinedTextField(
                value = caseDescription,
                onValueChange = { caseDescription = it },
                label = { Text("Breve Descripción", fontWeight = FontWeight.Medium) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4,
                placeholder = { Text("(150 caracteres max)", fontSize = 14.sp) }
            )

            // Selector de hora para la cita
            ExposedDropdownMenuBox(
                expanded = expandedHour,
                onExpandedChange = { expandedHour = !expandedHour }
            ) {
                OutlinedTextField(
                    value = selectedHour,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona Hora") }, // Campo de selección de hora
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHour)
                    },
                    modifier = Modifier
                        .menuAnchor() // Para que el menú aparezca justo bajo el campo
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedHour,
                    onDismissRequest = { expandedHour = false },
                    modifier = Modifier
                        .heightIn(max = 200.dp) // Limitar la altura máxima del menú
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium, // Aplicar esquinas redondeadas
                        color = MaterialTheme.colorScheme.background // Fondo blanco o el color del tema
                    ) {
                        Column {
                            availableHours.forEachIndexed { index, hour ->
                                DropdownMenuItem(
                                    text = { Text(hour) },
                                    onClick = {
                                        selectedHour = hour // Actualiza la hora seleccionada
                                        expandedHour = false
                                    }
                                )
                                // Añadir un Divider después de cada opción, excepto la última
                                if (index < availableHours.size - 1) {
                                    Divider()
                                }
                            }
                        }
                    }
                }
            }

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

            // Check para aceptar estudiantes en la cita
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
                    val selectedDate = Calendar.getInstance().apply {
                        timeInMillis = fechaSeleccionada ?: 0L
                    }
                    val today = Calendar.getInstance()

                    if (isFormComplete && fechaSeleccionada != null) {
                        if (selectedDate.before(today)) {
                            showDateErrorDialog = true // Mostrar error si la fecha es pasada
                        } else {
                            val fechaCita = "${java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaSeleccionada)}"

                            println(caseDescription.text)
                            println(selectedCategory)

                            viewModel.setAsesoria(caseDescription.text, selectedCategory)
                            viewModel.setCita(fechaCita, selectedHour)
                            resultado = "Solicitud enviada para el ${java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(fechaSeleccionada)} a las ${selectedHour}"
                            navController.navigateUp()
                        }
                    } else {
                        resultado = "Por favor, completa todos los espacios."
                    }
                }
            }) {
                Text("Mandar Solicitud")
            }

            // Mostrar resultado
            Text(resultado)
        }

    }

    if (showDatePicker) {
        val today = Calendar.getInstance()
        val context = LocalContext.current  // Obtener el contexto correcto para Jetpack Compose

        val datePickerDialog = android.app.DatePickerDialog(
            context, // Usar el contexto obtenido
            { _, year, month, dayOfMonth ->
                // Crear un objeto Calendar con la fecha seleccionada
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Validar si la fecha es anterior al día actual
                if (selectedDate.before(today)) {
                    showDateErrorDialog = true // Mostrar error si la fecha es inválida
                } else {
                    fechaSeleccionada = selectedDate.timeInMillis // Asignar la fecha seleccionada
                    showDatePicker = false // Cerrar el DatePicker
                }
            },
            today.get(Calendar.YEAR), // Año inicial
            today.get(Calendar.MONTH), // Mes inicial
            today.get(Calendar.DAY_OF_MONTH) // Día inicial
        )
        datePickerDialog.show()
    }

    if (showDateErrorDialog) {
        AlertDialog(
            onDismissRequest = { showDateErrorDialog = false },
            confirmButton = {
                TextButton(onClick = { showDateErrorDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Fecha no válida") },
            text = { Text("La fecha seleccionada no puede ser anterior a hoy. Por favor, selecciona una fecha válida.") }
        )
    }
}
