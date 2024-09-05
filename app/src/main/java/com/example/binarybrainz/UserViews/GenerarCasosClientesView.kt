package com.example.binarybrainz.UserViews

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var expanded by remember { mutableStateOf(false) } // Estado de expansión del menú

    val categories = listOf("Violencia Doméstica", "Sentencia de Divorcio", "Testamento", "Pensión Alimenticia")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("No te quedes callado") },
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
                .padding(16.dp)
        ) {
            // Botón desplegable para seleccionar una categoría
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded } // Cambia el estado cuando se hace clic
            ) {
                TextField(
                    value = selectedCategory,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Selecciona una categoría") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false } // Cierra el menú cuando se hace clic fuera
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false // Cierra el menú al seleccionar
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para correo
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para teléfono
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para subir identificación oficial (icono de pin)
            OutlinedButton(
                onClick = { /* Acción para subir identificación */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Subir Identificación")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Subir Identificación Oficial")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TextBox para descripción del caso (máximo 150 caracteres)
            TextField(
                value = caseDescription,
                onValueChange = {
                    if (it.text.length <= 150) caseDescription = it
                },
                label = { Text("Descripción breve del caso") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                visualTransformation = VisualTransformation.None,
                maxLines = 4,
                placeholder = { Text("Describe brevemente tu caso...") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("${caseDescription.text.length} / 150 caracteres")
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
