package com.example.binarybrainz.Clientes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarClientes(navController: NavController, clientName: String) {
    TopAppBar(
        title = {
            Text(
                text = clientName,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate("profile_cliente_view")// Navegar a la vista de perfil cuando esté disponible
                }
            ) {
                Icon(Icons.Default.Person, contentDescription = "Perfil de Cliente")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarClientesPreview() {
    BinaryBrainzTheme {
        TopBarClientes(
            navController = rememberNavController(),
            clientName = "Juan Pérez"
        )
    }
}
