import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadAsesoriaList()
        viewModel.loadUserNameList()
        asesorias = viewModel.asesoriaList
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

                // Título de la sección
                TitleSection(title = "Casos Pendientes")

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopEnd)
                ) {
                    TextButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text("FILTRAR POR: $filterType", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Abrir Filtro")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Gravedad") },
                            onClick = {
                                filterType = "Gravedad"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Fecha") },
                            onClick = {
                                filterType = "Fecha"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Ambos") },
                            onClick = {
                                filterType = "Ambos"
                                expanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(filteredAsesorias) { index, asesoria ->
                        AsesoriaRow(
                            navController = navController,
                            asesoria = asesoria,
                            usuarios = usuarios,
                            onEditStatusClick = {
                                selectedAsesoria = asesoria
                                showStatusDialog = true
                            }
                        )
                        if (index < asesorias.size - 1) {
                            Divider(thickness = 1.dp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }

    // Mostrar diálogo para cambiar el estado de la asesoría
    if (showStatusDialog && selectedAsesoria != null) {
        StatusDialog(
            asesoria = selectedAsesoria!!,
            onDismiss = { showStatusDialog = false },
            onStatusSelected = { status ->
                selectedAsesoria?.let {
                    it.status = status
                    showStatusDialog = false
                }
            }
        )
    }
}

@Composable
fun AsesoriaRow(navController: NavController, asesoria: Asesoria, usuarios: List<User>, onEditStatusClick: (Asesoria) -> Unit) {
    val cliente = usuarios.find { it.id == asesoria.cliente_id }

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
                    contentDescription = "Advertencia",
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
                        .clickable { navController.navigate("editar_casos_abogados/${asesoria.id}") }
                )
            }
        }
    }
}

@Composable
fun StatusDialog(asesoria: Asesoria, onDismiss: () -> Unit, onStatusSelected: (String) -> Unit) {
    var status by remember { mutableStateOf(asesoria.status) }
    var sliderValue by remember { mutableStateOf(1f) } // Gravedad del caso

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icono de cerrar (tacha)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Asignar Estado a el Caso #${asesoria.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Slider para seleccionar la gravedad del caso
                Text(
                    text = "Gravedad del caso: ${sliderValue.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    valueRange = 1f..5f,
                    steps = 3,  // Cambiado a 3 pasos para 5 niveles
                    colors = SliderDefaults.colors(
                        thumbColor = Color.DarkGray,
                        activeTrackColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onStatusSelected(status) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Asignar Estado", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun TitleSection(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black  // Cambiado a negro
        )
    }
}

