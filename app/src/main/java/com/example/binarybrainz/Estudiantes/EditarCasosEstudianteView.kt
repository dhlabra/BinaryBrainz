package com.example.binarybrainz.StudentViews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.Extras.Asesoria
import com.example.binarybrainz.Extras.User
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCasosEstudiantesView(navController: NavController, caseId: Int, viewModel: UserViewModel) {
    var isLoading by remember { mutableStateOf(false) }

    var asesorias by remember { mutableStateOf<List<Asesoria>>(emptyList()) }
    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadAsesoriaList()
        viewModel.loadUserNameList()
        asesorias = viewModel.asesoriaList.filter { it.id == caseId }
        users = viewModel.userList
        isLoading = false
    }

    var selectedStudent by remember { mutableStateOf<User?>(null) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                content = {
                    IconButton(
                        onClick = { navController.popBackStack() } // Acción para regresar a la vista anterior
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White
            )
        }
    ) { paddingValues ->
        asesorias.firstOrNull()?.let { detail ->
            val cliente = users.firstOrNull { it.id == detail.cliente_id }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Tarjeta de información
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "CLIENTE:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Text(
                            text = "${cliente?.nombre} ${cliente?.apellido}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)

                        // Información del teléfono
                        Text(
                            text = "TELÉFONO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = cliente?.celular ?: "No disponible",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)

                        Text(
                            text = "SERVICIO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.category,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)

                        Text(
                            text = "BREVE DESCRIPCIÓN:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                if (selectedStudent != null) {
                    Text(
                        text = "Caso asignado a: ${selectedStudent?.nombre} ${selectedStudent?.apellido}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }
            }
        } ?: run {
            // Si no se encuentra el caso, muestra un mensaje
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Caso no encontrado.")
            }
        }
    }
}
