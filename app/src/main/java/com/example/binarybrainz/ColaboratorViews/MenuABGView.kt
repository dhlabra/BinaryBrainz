package com.example.binarybrainz.ColaboratorViews

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuABGView(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Creación de Caso") },
                    selected = false,
                    onClick = {
                        navController.navigate("creacion_de_casos_view") {
                            popUpTo("menu_abg_view") { inclusive = true }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Casos Pendientes") },
                    selected = false,
                    onClick = { /* Navegación a Casos Pendientes */ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Historial") },
                    selected = false,
                    onClick = { /* Navegación a Historial */ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Agregar Estudiante") },
                    selected = false,
                    onClick = { /* Navegación a Agregar Estudiante */ }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Menú ABG",
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
        ) { paddingValues ->
            // Contenido principal de MenuABGView
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Contenido del Menú ABG")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuABGViewPreview() {
    BinaryBrainzTheme {
        MenuABGView(navController = rememberNavController())
    }
}
