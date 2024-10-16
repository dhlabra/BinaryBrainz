package com.example.binarybrainz.Estudiantes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.EditProfileDialog
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.DarkGrey // Importa el color DarkGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarEstudiante(navController: NavController, studentName: String, viewModel: UserViewModel) {
    var showDialog by remember { mutableStateOf(false) } // Controlar el pop-up
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadUserName()
        isLoading = false
    }

    TopAppBar(
        title = {
            Text(
                text = viewModel.userName.value, // Puedes ajustar el nombre del estudiante aquí
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado para el texto
            )
        },
        actions = {
            IconButton(
                onClick = {
                    showDialog = true // Mostrar el pop-up en lugar de cerrar sesión
            }) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil de Usuario",
                    tint = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado para el ícono
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkGrey // Aplica el color DarkGrey al fondo
        ),
        modifier = Modifier.fillMaxWidth() // Elimina padding para que ocupe todo el ancho
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
