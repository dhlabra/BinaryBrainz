package com.example.binarybrainz.Extras

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.R
import com.example.binarybrainz.ui.theme.DarkGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView(navController: NavController, viewModel: UserViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("Cliente") }
    var acceptTerms by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .padding(paddingValues)
                .safeDrawingPadding()
                .verticalScroll(rememberScrollState()), // Agregar scroll para que todo sea visible en pantallas más pequeñas
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.clinicapenal),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
            )
            EditNumberField(
                label = R.string.correo,
                leadingIcon = Icons.Filled.Email,
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.password,
                leadingIcon = Icons.Filled.Lock,
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = false,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.nombre,
                leadingIcon = Icons.Filled.Face,
                value = nombre,
                onValueChange = { nombre = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.apellido,
                leadingIcon = Icons.Filled.Face,
                value = apellido,
                onValueChange = { apellido = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.celular,
                leadingIcon = Icons.Filled.Phone,
                value = celular,
                onValueChange = { celular = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            // Checkbox para Aceptar los Términos y Condiciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.onSurface, // Fondo negro cuando está marcado
                        uncheckedColor = MaterialTheme.colorScheme.onSurface, // Fondo blanco cuando no está marcado
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary // Palomita blanca
                    )
                )
                Text(
                    text = "Acepto los términos y condiciones.",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f) // Para que el texto ocupe todo el espacio disponible
                )
                IconButton(onClick = { showTermsDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Ver Términos y Condiciones"
                    )
                }
            }

            // Botón para registrar
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (acceptTerms) {
                        viewModel.signUp(email, password, rol, nombre, apellido, celular)
                    } else {
                        // Mostrar un mensaje de error si no aceptó los términos
                        viewModel.errorMessage.value = "Debes aceptar los términos y condiciones."
                    }
                },
                enabled = !isLoading && acceptTerms,
                colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Crear Cuenta", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            // Mostrar el mensaje de error
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Texto de "Regresar al Login"
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = AnnotatedString(
                    "Regresar al Login",
                    spanStyle = androidx.compose.ui.text.SpanStyle(
                        color = Color.Black,
                        textDecoration = TextDecoration.Underline
                    )
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    // Navegar a la pantalla de Login
                    navController.navigate("login_view")
                }
            )
        }
    }

    // Diálogo con los términos y condiciones con scroll
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("Cerrar")
                }
            },
            title = { Text("Términos y Condiciones") },
            text = {
                Column(
                    modifier = Modifier
                        .heightIn(max = 300.dp) // Ajustar la altura máxima del pop-up para permitir scroll
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("""
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
                    """.trimIndent())
                }
            }
        )
    }
}