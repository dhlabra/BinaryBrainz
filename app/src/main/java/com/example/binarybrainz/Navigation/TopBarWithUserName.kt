package com.example.binarybrainz.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithUserName(navController: NavController, userName: String) {
    TopAppBar(
        title = {
            Text(text = userName, fontSize = 20.sp)
        },
        actions = {
            IconButton(onClick = { navController.navigate("profile_cliente_view") }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Perfil")
            }
        }
    )
}
