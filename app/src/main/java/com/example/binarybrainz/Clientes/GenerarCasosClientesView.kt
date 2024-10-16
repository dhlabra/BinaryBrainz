import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.DarkGrey
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
    val currentTime = Calendar.getInstance()
    val scope = rememberCoroutineScope()
    var expandedHour by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf("") }
    val availableHours = listOf("10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")
    var fechaSeleccionada by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showDateErrorDialog by remember { mutableStateOf(false) }

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
        "Herencia y Sucesión"
    )
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
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column {
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
                }
            }

            // Botón para abrir el selector de fecha modal
            Button(
                onClick = { showDatePicker = true },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGrey) // Color del botón DarkGrey
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Seleccionar Fecha")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Seleccionar Fecha", color = MaterialTheme.colorScheme.onPrimary) // Texto en contraste con DarkGrey
            }

            // Mostrar la fecha seleccionada
            Text(
                text = fechaSeleccionada?.let {
                    java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                } ?: "No se ha seleccionado ninguna fecha"
            )

            // Check de términos y condiciones con el ícono de interrogación
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = DarkGrey, // Cambia el color a DarkGrey cuando está seleccionado
                        uncheckedColor = MaterialTheme.colorScheme.onSurface, // Color cuando no está seleccionado
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary // Color de la marca de verificación
                    )
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
                    colors = CheckboxDefaults.colors(
                        checkedColor = DarkGrey, // Cambia el color a DarkGrey cuando está seleccionado
                        uncheckedColor = MaterialTheme.colorScheme.onSurface, // Color cuando no está seleccionado
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary // Color de la marca de verificación
                    )
                )
                Text(
                    text = "Acepto que alumnos estén presentes en la cita.",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            // Pop-up de términos y condiciones
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
1. Aceptación de los Términos
Al acceder y utilizar nuestra aplicación, aceptas cumplir con estos Términos y Condiciones de Uso, así como con cualquier ley y regulación aplicable. Si no estás de acuerdo con estos términos, te pedimos que no utilices nuestra aplicación.

2. Uso de la Aplicación
Nuestra aplicación está diseñada para proporcionar servicios de gestión de casos y asesorías. El uso de la aplicación debe ser para fines lícitos y está prohibido cualquier uso que viole las leyes aplicables.

3. Creación y Gestión de Casos
El usuario es responsable de la información ingresada al crear y gestionar casos dentro de la aplicación. Es fundamental que todos los datos proporcionados sean precisos y verídicos para garantizar el correcto funcionamiento de la plataforma.

4. Privacidad
Nos comprometemos a proteger tu privacidad y la confidencialidad de la información proporcionada a través de la aplicación. Los datos personales se manejarán de acuerdo con nuestra Política de Privacidad, la cual puedes consultar en el apartado correspondiente.

5. Responsabilidades del Usuario
El usuario acepta utilizar la aplicación de manera responsable y ética, evitando cualquier actividad que comprometa la seguridad, integridad o funcionamiento de la aplicación.

6. Limitación de Responsabilidad
No somos responsables de daños directos, indirectos o consecuentes que resulten del uso o la incapacidad de utilizar la aplicación. El uso de la aplicación es bajo tu propio riesgo.

7. Cambios en los Términos
Nos reservamos el derecho de modificar estos términos en cualquier momento. Te recomendamos revisar esta página periódicamente para mantenerte informado sobre cualquier actualización.

8. Contacto
Si tienes alguna pregunta sobre estos términos, puedes contactarnos a través de los medios disponibles en la aplicación.
                                """.trimIndent(),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { showTermsDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkGrey) // Color del botón DarkGrey
                            ) {
                                Text("Cerrar", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón para mandar la solicitud
            Button(
                onClick = {
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
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGrey) // Color del botón DarkGrey
            ) {
                Text("Mandar Solicitud", color = MaterialTheme.colorScheme.onPrimary) // Texto en contraste con DarkGrey
            }

            // Mostrar resultado
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
