import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.User
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun AsignarRolesScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var usuarios by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var showRoleDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadUserNameList()
        usuarios = viewModel.userList
        isLoading = false
    }

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

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {

                    LazyColumn {
                        itemsIndexed(usuarios) { index, user ->
                            UsuarioRow(
                                navController,
                                usuario = "${user.nombre} ${user.apellido}",
                                role = user.role,
                                onEditClick = {
                                    selectedUser = user
                                    showRoleDialog = true
                                }
                            )
                            if (index < usuarios.size - 1) {
                                Divider(thickness = 1.dp, color = Color.Gray)
                            }
                        }
                    }

                    if (showRoleDialog) {
                        AsignarRoleDialog(
                            usuario = "${selectedUser?.nombre} ${selectedUser?.apellido}",
                            id = "${selectedUser?.id}",
                            currentRole = selectedUser?.role ?: "Sin rol",
                            onDismiss = { showRoleDialog = false },
                            onRoleSelected = { role ->
                                selectedUser?.let { it.role = role }
                                showRoleDialog = false
                            },
                            viewModel = viewModel
                        )
                    }
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
            shape = RoundedCornerShape(16.dp)
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
    id: String,
    currentRole: String,
    onDismiss: () -> Unit,
    onRoleSelected: (String) -> Unit,
    viewModel: UserViewModel
) {
    var selectedRole by remember { mutableStateOf(currentRole) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Asignar rol a $usuario")
        },
        text = {
            Column {
                RoleRadioButton(
                    role = "Abogado",
                    selectedRole = selectedRole,
                    onRoleSelected = { selectedRole = "Abogado" }
                )
                RoleRadioButton(
                    role = "Estudiante",
                    selectedRole = selectedRole,
                    onRoleSelected = { selectedRole = "Estudiante" }
                )
                RoleRadioButton(
                    role = "Cliente",
                    selectedRole = selectedRole,
                    onRoleSelected = { selectedRole = "Cliente" }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.setRole(selectedRole, id)
                onRoleSelected(selectedRole)
            }) {
                Text("Asignar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }

    )
}

@Composable
fun RoleRadioButton(role: String, selectedRole: String, onRoleSelected: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = { onRoleSelected() })
    ) {
        RadioButton(
            selected = (role == selectedRole),
            onClick = { onRoleSelected() },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Black, // Color negro para el botón seleccionado
                unselectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = role, fontSize = 16.sp)
    }
}
