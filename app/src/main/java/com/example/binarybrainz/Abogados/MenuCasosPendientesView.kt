import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados
import java.text.SimpleDateFormat
import java.util.*

data class Case(
    val id: String,
    var date: Date = Date(),
    var severity: Int? = null
)

@Composable
fun MenuCasosPendientesScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var filterType by remember { mutableStateOf("Por Gravedad") }  // Estado para el tipo de filtro
    var expanded by remember { mutableStateOf(false) }  // Estado para el menú desplegable
    var showSeverityDialog by remember { mutableStateOf(false) }
    var selectedCase by remember { mutableStateOf<Case?>(null) }  // Caso seleccionado para cambiar gravedad

    var cases by remember {
        mutableStateOf(
            listOf(
                Case("123", Date(), 7),
                Case("333", Date(), null),
                Case("475", Date(), 9),
                Case("758", Date(), 5)
            )
        )
    } // Lista de casos

    // Aplicar el filtro a la lista de casos
    val sortedCases = when (filterType) {
        "Gravedad" -> cases.filter { it.severity != null }.sortedByDescending { it.severity }  // Orden por gravedad
        "Fecha" -> cases.sortedBy { it.date }  // Orden por fecha
        "Ambos" -> cases.filter { it.severity != null }.sortedWith(compareByDescending<Case> { it.severity }.thenBy { it.date }) // Orden por gravedad y fecha
        else -> cases
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

                TitleSection()

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

                CasesList(
                    navController = navController,
                    cases = sortedCases,
                    onEditSeverityClick = { case ->
                        selectedCase = case
                        showSeverityDialog = true
                    }
                )
            }
        }
    }

    // Mostrar dialog para seleccionar la gravedad del caso
    if (showSeverityDialog && selectedCase != null) {
        SeverityDialog(
            case = selectedCase!!,
            onDismiss = { showSeverityDialog = false },
            onSeveritySelected = { severity ->
                selectedCase?.let {
                    it.severity = severity
                    showSeverityDialog = false
                }
            }
        )
    }
}

@Composable
fun TitleSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Casos Pendientes",
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
fun CasesList(navController: NavController, cases: List<Case>, onEditSeverityClick: (Case) -> Unit) {
    LazyColumn {
        items(cases) { case ->
            CaseRow(navController = navController, case = case, onEditSeverityClick = onEditSeverityClick)
            Divider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun CaseRow(navController: NavController, case: Case, onEditSeverityClick: (Case) -> Unit) {
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
                    text = "Caso #${case.id}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fecha: ${SimpleDateFormat("dd/MM/yyyy").format(case.date)}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Gravedad: ${case.severity?.toString() ?: "Sin asignar"}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Row {
                // Icono para editar la gravedad del caso
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Editar Gravedad",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEditSeverityClick(case) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Icono para editar el caso (ya existente)
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar Caso",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.navigate("edit_case_view/${case.id}") }
                )
            }
        }
    }
}

@Composable
fun SeverityDialog(case: Case, onDismiss: () -> Unit, onSeveritySelected: (Int) -> Unit) {
    var severity by remember { mutableStateOf(case.severity ?: 5) } // Mantener el estado actual de gravedad

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
                    text = "Asignar Gravedad al Caso #${case.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Slider(
                    value = severity.toFloat(),
                    onValueChange = { severity = it.toInt() },
                    valueRange = 1f..10f,
                    steps = 9
                )
                Text(text = "Gravedad: $severity")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onSeveritySelected(severity) }) {
                    Text("Asignar Gravedad")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        }
    }
}
