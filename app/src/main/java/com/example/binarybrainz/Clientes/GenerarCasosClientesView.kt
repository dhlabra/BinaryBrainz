import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerarCasosClientesView(navController: NavController, viewModel: UserViewModel) {
    var selectedCategory by remember { mutableStateOf("") }
    var caseDescription by remember { mutableStateOf(TextFieldValue("")) }
    var acceptTerms by remember { mutableStateOf(false) }
    var acceptStudents by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf("") }
    var showTermsDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    // Lista de servicios sacada del archivo MasInformacionView
    val services = listOf(
        "Violencia Doméstica",
        "Sentencia de Divorcio",
        "Testamento",
        "Pensión Alimenticia",
        "Acoso Laboral",
        "Adopción",
        "Contratos de Arrendamiento",
        "Custodia de Menores",
        "Despido Injustificado",
        "Herencia y Sucesión",
        "Clínica Penal"
    )

    val currentTime = Calendar.getInstance()
    val scope = rememberCoroutineScope()
    var expandedHour by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf("") }
    val availableHours = listOf("10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")
    var fechaSeleccionada by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showDateErrorDialog by remember { mutableStateOf(false) }

    val isFormComplete = selectedCategory.isNotBlank() && caseDescription.text.isNotBlank() && acceptTerms && acceptStudents && selectedHour.isNotBlank()

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
                .verticalScroll(rememberScrollState()),
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
                        .menuAnchor()
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
                    label = { Text("Selecciona Hora") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHour)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedHour,
                    onDismissRequest = { expandedHour = false },
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    availableHours.forEachIndexed { index, hour ->
                        DropdownMenuItem(
                            text = { Text(hour) },
                            onClick = {
                                selectedHour = hour
                                expandedHour = false
                            }
                        )
                        if (index < availableHours.size - 1) {
                            Divider()
                        }
                    }
                }
            }

            Button(onClick = { showDatePicker = true }) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Seleccionar Fecha")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Seleccionar Fecha")
            }

            Text(
                text = fechaSeleccionada?.let {
                    java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                } ?: "No se ha seleccionado ninguna fecha"
            )

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
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { showTermsDialog = true }) {
                    Icon(imageVector = Icons.Filled.List, contentDescription = "Términos y Condiciones")
                }
            }

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

            if (showTermsDialog) {
                Dialog(onDismissRequest = { showTermsDialog = false }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Términos y Condiciones de Uso",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = """
                                    [Términos detallados aquí...]
                                """.trimIndent(),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { showTermsDialog = false }) {
                                Text("Cerrar")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                scope.launch {
                    val selectedDate = Calendar.getInstance().apply {
                        timeInMillis = fechaSeleccionada ?: 0L
                    }
                    val today = Calendar.getInstance()

                    if (isFormComplete && fechaSeleccionada != null) {
                        if (selectedDate.before(today)) {
                            showDateErrorDialog = true
                        } else {
                            val fechaCita = "${java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fechaSeleccionada)}"
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

            Text(resultado)
        }
    }

    if (showDatePicker) {
        val today = Calendar.getInstance()
        val context = LocalContext.current

        val datePickerDialog = android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                if (selectedDate.before(today)) {
                    showDateErrorDialog = true
                } else {
                    fechaSeleccionada = selectedDate.timeInMillis
                    showDatePicker = false
                }
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
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
