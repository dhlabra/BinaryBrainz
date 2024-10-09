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
import com.example.binarybrainz.Extras.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerAbogados(navController: NavController, drawerState: DrawerState, viewModel: UserViewModel) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier.width(240.dp)
    ) {
        IconButton(onClick = { scope.launch { drawerState.close() } }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Cerrar menú")
        }
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
            label = { Text(text = "Asignar Roles") },
            selected = false,
            onClick = {  }
        )
    }
}
