package com.example.binarybrainz.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAbogados(
    navController: NavController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(
                text = "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Abrir menú")
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate("login_view") }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Perfil de Usuario"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
