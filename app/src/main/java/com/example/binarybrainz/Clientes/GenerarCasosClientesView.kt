package com.example.binarybrainz.Clientes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerarCasosClientesView(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("") }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var caseDescription by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }
    var acceptTerms by remember { mutableStateOf(false) }
    var acceptStudents by remember { mutableStateOf(false) }

    // Categorías de servicio para el dropdown
    val categories = listOf("Violencia Doméstica", "Sentencia de Divorcio", "Testamento", "Pensión Alimenticia")

    // Condición para habilitar el botón "Siguiente"
    val isFormComplete = name.text.isNotBlank() && email.text.isNotBlank() && phone.text.isNotBlank()
            && caseDescription.text.isNotBlank() && selectedCategory.isNotBlank()
            && acceptTerms && acceptStudents

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitud de Ayuda") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)  // Espaciado entre los elementos
        ) {
            // Campo para nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo para correo
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo para teléfono
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para subir identificación oficial (icono de pin)
            OutlinedButton(
                onClick = { /* Acción para subir identificación */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Subir Identificación")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Subir Identificación Oficial")
            }

            // Botón desplegable para seleccionar una categoría de servicio
            Box {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Servicio") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Seleccionar servicio")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            // TextBox para descripción del caso (máximo 150 caracteres)
            TextField(
                value = caseDescription,
                onValueChange = {
                    if (it.text.length <= 150) caseDescription = it
                },
                label = { Text("Breve Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),  // Ajusté la altura para que sea más manejable
                visualTransformation = VisualTransformation.None,
                maxLines = 4,
                placeholder = { Text("(150 caractéres max)") }
            )

            // Check de términos y condiciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it }
                )
                Text(text = "Acepto los términos y condiciones.")
            }

            // Check de aceptación de estudiantes en la cita
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptStudents,
                    onCheckedChange = { acceptStudents = it }
                )
                Text(text = "Acepto que alumnos estén presentes en la cita.")
            }

            Text(text = "Uno de nuestros abogados se pondrá en contacto para agendar la cita.")

            Spacer(modifier = Modifier.weight(1f))  // Empuja el botón hacia abajo

            // Botón para navegar al calendario
            Button(
                onClick = {
                    if (isFormComplete) {
                        navController.navigate("horarios_disponibles_view")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = isFormComplete  // Deshabilitado hasta que el formulario esté completo
            ) {
                Text(text = "Siguiente")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenerarCasosClientesViewPreview() {
    BinaryBrainzTheme {
        GenerarCasosClientesView(navController = rememberNavController())
    }
}
