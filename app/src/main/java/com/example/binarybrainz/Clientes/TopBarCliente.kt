package com.example.binarybrainz.Clientes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarClientes(navController: NavController, clientName: String, viewModel: UserViewModel) {
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
                    viewModel.signOut(navController)
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