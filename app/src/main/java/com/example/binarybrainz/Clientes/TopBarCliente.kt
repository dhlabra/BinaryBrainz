package com.example.binarybrainz.Clientes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.DarkGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarClientes(navController: NavController, clientName: String, viewModel: UserViewModel) {
    var showDialog by remember { mutableStateOf(false) } // Controlar el pop-up

    TopAppBar(
        title = {
            Text(
                text = clientName,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(
                onClick = {
                    showDialog = true // Mostrar el pop-up en lugar de cerrar sesión
                }
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil de Cliente",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkGrey
        ),
        modifier = Modifier.fillMaxWidth()
    )

    // Si showDialog es true, mostrar el pop-up
    if (showDialog) {
        EditProfileDialog(
            navController = navController,
            viewModel = viewModel,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun EditProfileDialog(navController: NavController, viewModel: UserViewModel, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("Ricardo") }
    var surname by remember { mutableStateOf("Chapa") }
    var phone by remember { mutableStateOf("123-456-7890") }
    var email by remember { mutableStateOf("ricardo@ejemplo.com") }
    var editingField by remember { mutableStateOf<String?>(null) } // Almacena el campo que se está editando

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Título
                Text(
                    text = "Editar Perfil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Secciones de edición con separadores
                ProfileItem(
                    label = "Nombre",
                    value = name,
                    isEditing = editingField == "name",
                    onEditClick = { editingField = if (editingField == "name") null else "name" },
                    onValueChange = { name = it }
                )
                Divider() // Agrega un separador

                ProfileItem(
                    label = "Apellido",
                    value = surname,
                    isEditing = editingField == "surname",
                    onEditClick = { editingField = if (editingField == "surname") null else "surname" },
                    onValueChange = { surname = it }
                )
                Divider() // Agrega un separador

                ProfileItem(
                    label = "Teléfono",
                    value = phone,
                    isEditing = editingField == "phone",
                    onEditClick = { editingField = if (editingField == "phone") null else "phone" },
                    onValueChange = { phone = it }
                )
                Divider() // Agrega un separador

                ProfileItem(
                    label = "Correo",
                    value = email,
                    isEditing = editingField == "email",
                    onEditClick = { editingField = if (editingField == "email") null else "email" },
                    onValueChange = { email = it }
                )
                Divider() // Agrega un separador


                Spacer(modifier = Modifier.height(16.dp))

                // Botones de Cerrar y Cerrar Sesión
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    // Botón de Cerrar (solo cierra el pop-up)
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)
                    ) {
                        Text(text = "Cerrar", color = Color.White)
                    }

                    // Botón de Cerrar Sesión (cierra sesión y redirige)
                    Button(
                        onClick = {
                            viewModel.signOut()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)
                    ) {
                        Text(text = "Cerrar Sesión", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(
    label: String,
    value: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEditing) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f)
            )
        } else {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Editar",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .clickable { onEditClick() }
                .padding(4.dp)
        )
    }
}
