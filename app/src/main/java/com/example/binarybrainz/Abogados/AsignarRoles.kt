package com.example.binarybrainz.Abogados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun AsignarRolesScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val usuarios = listOf("Juan Pérez", "Ana García", "Carlos Sánchez", "Laura Rodríguez")
    var showRoleDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf("") }
    var roles = remember { mutableStateMapOf<String, String>() } // Mapa para almacenar roles asignados

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerAbogados(navController = navController, drawerState = drawerState, viewModel)
        }
    ) {
        Scaffold(
            topBar = {
                TopBarAbogados(
                    navController = navController,
                    drawerState = drawerState,
                    viewModel
                ) {
                    navController.navigate("login_view")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Asignar Roles",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn {
                    items(usuarios) { usuario ->
                        UsuarioRow(
                            navController,
                            usuario,
                            role = roles[usuario] ?: "SA", // Mostrar rol asignado o "SA" si no hay rol
                            onEditClick = {
                                selectedUser = usuario
                                showRoleDialog = true
                            }
                        )
                        Divider(thickness = 1.dp, color = Color.Gray)
                    }
                }

                if (showRoleDialog) {
                    AsignarRoleDialog(
                        selectedUser,
                        onDismiss = { showRoleDialog = false },
                        onRoleSelected = { role ->
                            roles[selectedUser] = role
                            showRoleDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UsuarioRow(navController: NavController, usuario: String, role: String, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = usuario,
                fontSize = 18.sp,
            )
            Text(
                text = role, // Mostrar el rol asignado
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light
            )
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        ) {
            IconButton(onClick = { onEditClick() }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}

@Composable
fun AsignarRoleDialog(
    usuario: String,
    onDismiss: () -> Unit,
    onRoleSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Asignar rol a $usuario")
        },
        text = {
            Column {
                RoleButton(role = "Abogado", onRoleSelected = { onRoleSelected("Abogado") })
                RoleButton(role = "Estudiante", onRoleSelected = { onRoleSelected("Estudiante") })
                RoleButton(role = "Cliente", onRoleSelected = { onRoleSelected("Cliente") })
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
fun RoleButton(role: String, onRoleSelected: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = {
            onRoleSelected()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ) {
        Text(text = role)
    }
}
