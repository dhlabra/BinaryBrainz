package com.example.binarybrainz.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerAbogados(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier.width(240.dp)
    ) {
        IconButton(onClick = { scope.launch { drawerState.close() } }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Cerrar menú")
        }
        HorizontalDivider()
        NavigationDrawerItem(
            label = { Text(text = "Creación de Caso") },
            selected = false,
            onClick = { navController.navigate("creacion_caso_view") }
        )
        NavigationDrawerItem(
            label = { Text(text = "Casos Pendientes") },
            selected = false,
            onClick = { navController.navigate("menu_casos_pendientes_view") }
        )
        NavigationDrawerItem(
            label = { Text(text = "Historial") },
            selected = false,
            onClick = { navController.navigate("menu_historial_view") }
        )
        NavigationDrawerItem(
            label = { Text(text = "Agregar Estudiante") },
            selected = false,
            onClick = { /* Navegación a Agregar Estudiante */ }
        )
    }
}
