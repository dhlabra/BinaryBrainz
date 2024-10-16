package com.example.binarybrainz.Clientes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.DarkGrey // Importa el color DarkGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarClientes(navController: NavController, clientName: String, viewModel: UserViewModel) {
    TopAppBar(
        title = {
            Text(
                text = clientName,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado para el texto
            )
        },
        actions = {
            IconButton(
                onClick = {
                    viewModel.signOut() // Acción para cerrar sesión
                }
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil de Cliente",
                    tint = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado para el ícono
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkGrey // Aplica el color DarkGrey al fondo
        ),
        modifier = Modifier.fillMaxWidth() // Elimina el padding para ocupar todo el ancho
    )
}
