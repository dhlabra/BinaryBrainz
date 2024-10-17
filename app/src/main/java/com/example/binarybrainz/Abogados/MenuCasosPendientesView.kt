import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.Asesoria
import com.example.binarybrainz.Extras.User
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuAsesoriasPendientesScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var filterType by remember { mutableStateOf("Por Categoría") }  // Estado para el tipo de filtro
    var expanded by remember { mutableStateOf(false) }  // Estado para el menú desplegable
    var showStatusDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedAsesoria by remember { mutableStateOf<Asesoria?>(null) }  // Asesoría seleccionada para cambiar estado

    var asesorias by remember { mutableStateOf<List<Asesoria>>(emptyList()) }
    var usuarios by remember { mutableStateOf<List<User>>(emptyList()) }

    // Cargar asesorías desde el ViewModel
    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadAsesoriaList()
        viewModel.loadUserNameList()
        asesorias =
            viewModel.asesoriaList
        usuarios = viewModel.userList
        isLoading = false
    }

    // Aplicar el filtro a la lista de asesorías
    val filteredAsesorias = when (filterType) {
        "Categoría" -> asesorias.groupBy { it.category }.values.flatten()
        "Fecha" -> asesorias.sortedBy { it.created_at }
        else -> asesorias
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerAbogados(navController = navController, drawerState = drawerState, viewModel)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Solicitud de Ayuda", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                TitleSection(title = "Asesorías Pendientes")

                Spacer(modifier = Modifier.height(24.dp))

                FilterSection(
                    filterType = filterType,
                    onFilterSelected = { selectedFilter ->
                        filterType = selectedFilter
                    },
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    onClick = { expanded = true }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AsesoriasList(
                    navController = navController,
                    asesorias = filteredAsesorias,
                    usuarios = usuarios,
                    onEditStatusClick = { asesoria ->
                        selectedAsesoria = asesoria
                        showStatusDialog = true
                    }
                )
            }
        }
    }

    // Mostrar dialog para seleccionar el estado de la asesoría
    if (showStatusDialog && selectedAsesoria != null) {
        StatusDialog(
            asesoria = selectedAsesoria!!,
            onDismiss = { showStatusDialog = false },
            onStatusSelected = { status ->
                selectedAsesoria?.let {
                    it.status = status
                    showStatusDialog = false
                    //viewModel.updateAsesoria(it)  // Actualizar en Supabase a través del ViewModel
                }
            }
        )
    }
}

@Composable
fun TitleSection(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FilterSection(
    filterType: String,
    onFilterSelected: (String) -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier
                    .clickable { onClick() }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FILTRAR POR: $filterType",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            DropdownMenuItem(
                text = { Text("Gravedad") },
                onClick = {
                    onFilterSelected("Gravedad")
                    onDismissRequest()
                }
            )
            DropdownMenuItem(
                text = { Text("Fecha") },
                onClick = {
                    onFilterSelected("Fecha")
                    onDismissRequest()
                }
            )
            DropdownMenuItem(
                text = { Text("Ambos") },
                onClick = {
                    onFilterSelected("Ambos")
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
fun AsesoriasList(navController: NavController, asesorias: List<Asesoria>, usuarios: List<User>, onEditStatusClick: (Asesoria) -> Unit) {
    LazyColumn {
        items(asesorias) { asesoria ->
            AsesoriaRow(navController = navController, asesoria = asesoria, usuarios = usuarios, onEditStatusClick = onEditStatusClick)
            Divider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun AsesoriaRow(navController: NavController, asesoria: Asesoria, usuarios: List<User>, onEditStatusClick: (Asesoria) -> Unit) {
    val cliente = usuarios.find { it.id == asesoria.cliente_id }
    println("AQUI ESTA ASESORIAS: ${asesoria.cliente_id}")
    println("AQUI ESTA USUARIOS: ${usuarios}")

    val nombreCliente = cliente?.nombre ?: "Desconocido"
    val apellidoCliente = cliente?.apellido ?: ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Caso #${asesoria.id}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Categoría: ${asesoria.category}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Estado: ${asesoria.status}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Cliente: $nombreCliente $apellidoCliente",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Row {
                // Icono para editar el estado de la asesoría
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Editar Estado",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEditStatusClick(asesoria) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Icono para navegar a la vista de detalles/editar la asesoría
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar Asesoría",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.navigate("edit_asesoria_view/${asesoria.id}") }
                )
            }
        }
    }
}

@Composable
fun StatusDialog(asesoria: Asesoria, onDismiss: () -> Unit, onStatusSelected: (String) -> Unit) {
    var status by remember { mutableStateOf(asesoria.status) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Asignar Estado a la Asesoría #${asesoria.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                DropdownMenu(
                    expanded = true,
                    onDismissRequest = onDismiss
                ) {
                    DropdownMenuItem(
                        text = { Text("Pendiente") },
                        onClick = {
                            status = "Pendiente"
                            onStatusSelected(status)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("En proceso") },
                        onClick = {
                            status = "En proceso"
                            onStatusSelected(status)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Finalizado") },
                        onClick = {
                            status = "Finalizado"
                            onStatusSelected(status)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { onStatusSelected(status) }) {
                    Text("Asignar Estado")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        }
    }
}

