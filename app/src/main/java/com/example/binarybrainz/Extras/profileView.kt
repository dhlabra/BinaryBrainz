package com.example.binarybrainz.Extras

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileClienteView(navController: NavController, viewModel: UserViewModel) {
    var name by remember { mutableStateOf("Nombre Cliente") }
    var email by remember { mutableStateOf("correo@ejemplo.com") }
    var phone by remember { mutableStateOf("1234567890") }
    var isEditingName by remember { mutableStateOf(false) }
    var isEditingEmail by remember { mutableStateOf(false) }
    var isEditingPhone by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = name, fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menucliente_view") }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Nombre
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isEditingName) {
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    IconButton(onClick = { isEditingName = !isEditingName }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Nombre")
                    }
                }

                // Correo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isEditingEmail) {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Correo") },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            text = email,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    IconButton(onClick = { isEditingEmail = !isEditingEmail }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Correo")
                    }
                }

                // Teléfono
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isEditingPhone) {
                        TextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Teléfono") },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            text = phone,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    IconButton(onClick = { isEditingPhone = !isEditingPhone }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Teléfono")
                    }
                }

                // Botón para cerrar sesión
                Button(
                    onClick = {
                        viewModel.signOut(navController)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                ) {
                    Text(text = "Cerrar Sesión")
                }
            }
        }
    )
}

