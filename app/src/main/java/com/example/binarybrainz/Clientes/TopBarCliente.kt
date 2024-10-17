package com.example.binarybrainz.Clientes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.EditProfileDialog
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.DarkGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarClientes(navController: NavController, viewModel: UserViewModel) {
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadUser()
        isLoading = false
    }

    TopAppBar(
        title = {
            val user = viewModel.user.value
            Text(
                text = if (user != null) "Bienvenido ${user.nombre}" else "",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado para el texto
            )
        },
        actions = {
            // Agregar texto "Mi caso" al lado del ícono de perfil
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mi caso",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable {
                            // Navega a la vista de progreso del caso
                            navController.navigate("cita_cliente_updates_view")
                        }
                        .padding(end = 8.dp)
                )
                IconButton(
                    onClick = {
                        showDialog = true // Mostrar el pop-up en lugar de cerrar sesión
                    }
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Perfil de Cliente",
                        tint = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado para el ícono
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkGrey // Aplica el color DarkGrey al fondo
        ),
        modifier = Modifier.fillMaxWidth() // Elimina el padding para ocupar todo el ancho
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
