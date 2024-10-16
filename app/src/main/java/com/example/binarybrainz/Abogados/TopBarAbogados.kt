package com.example.binarybrainz.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.EditProfileDialog
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.DarkGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAbogados(
    navController: NavController,
    drawerState: DrawerState,
    viewModel: UserViewModel,
    onUserIconClick: () -> Unit // Parámetro para definir la acción al hacer click en el ícono de usuario
) {
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Panel de Abogados", // Ajusta el texto del título si lo deseas
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary // Usa el color adecuado para el texto
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                // Corrección: Asegúrate de que `drawerState` esté bien referenciado y funcione
                scope.launch {
                    drawerState.open() // Abre el menú lateral (drawer)
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Abrir menú",
                    tint = MaterialTheme.colorScheme.onPrimary // Aplica el color adecuado al ícono
                )
            }
        },
        actions = {
            IconButton(onClick = {
                showDialog = true
            } ) { // Acción personalizada para el ícono de usuario
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Perfil de Usuario",
                    tint = MaterialTheme.colorScheme.onPrimary // Color del ícono de perfil
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkGrey // Aplica el color DarkGrey al fondo
        ),
        modifier = Modifier.fillMaxWidth() // Elimina padding y ocupa todo el ancho
    )
    if (showDialog) {
        EditProfileDialog(navController, viewModel) {
            showDialog = false
        }
    }
}

