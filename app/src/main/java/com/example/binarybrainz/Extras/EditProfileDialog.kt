package com.example.binarybrainz.Extras

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.binarybrainz.ui.theme.DarkGrey
import kotlinx.coroutines.launch

@Composable
fun EditProfileDialog(navController: NavController, viewModel: UserViewModel, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var editingField by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadUser()
        isLoading = false
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        val user = viewModel.user.value
        name = user?.nombre ?: ""
        surname = user?.apellido ?: ""
        phone = user?.celular ?: ""

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
                // Ícono de tacha para cerrar el diálogo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Cerrar",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() }
                    )
                }

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
                Divider()

                ProfileItem(
                    label = "Apellido",
                    value = surname,
                    isEditing = editingField == "surname",
                    onEditClick = { editingField = if (editingField == "surname") null else "surname" },
                    onValueChange = { surname = it }
                )
                Divider()

                ProfileItem(
                    label = "Teléfono",
                    value = phone,
                    isEditing = editingField == "phone",
                    onEditClick = { editingField = if (editingField == "phone") null else "phone" },
                    onValueChange = { phone = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botones de Guardar y Cerrar Sesión
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    // Botón de Guardar
                    Button(
                        onClick = {
                            val updates = mutableMapOf<String, String>()
                            if (name != viewModel.user.value?.nombre) updates["nombre"] = name
                            if (surname != viewModel.user.value?.apellido) updates["apellido"] = surname
                            if (phone != viewModel.user.value?.celular) updates["celular"] = phone

                            if (updates.isNotEmpty()) {
                                coroutineScope.launch {
                                    viewModel.updateUser(updates)
                                    editingField = null
                                    onDismiss()
                                }
                            } else {
                                onDismiss() // Si no hay cambios, solo cerrar el diálogo
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrey)
                    ) {
                        Text(text = "Guardar", color = Color.White)
                    }

                    // Botón de Cerrar Sesión
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
