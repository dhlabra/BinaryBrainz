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
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCasosAbogadosView(navController: NavController, caseId: String) {
    val casesDetail = listOf(
        CaseDetail(
            id = "123",
            name = "Fernando González Cárdenas",
            email = "Fgzz01@outlook.com",
            phone = "8124013672",
            service = "Testamento",
            description = "Mi padre murió y quiero recuperar su herencia pero no sé cómo, quisiera ayuda. Gracias."
        ),
        CaseDetail(
            id = "333",
            name = "Ricardo Chapa",
            email = "rchapa@correo.com",
            phone = "8124020304",
            service = "Divorcio",
            description = "Necesito asesoría para el proceso de divorcio."
        ),
        CaseDetail(
            id = "475",
            name = "Rodrigo González",
            email = "rodrigo@gmail.com",
            phone = "8111234567",
            service = "Custodia de menores",
            description = "Estoy buscando la custodia de mi hijo."
        ),
        CaseDetail(
            id = "758",
            name = "Marcelo Cárdenas",
            email = "marcelo@hotmail.com",
            phone = "8123344556",
            service = "Defensa penal",
            description = "Necesito defensa legal en un caso penal."
        )
    )

    val caseDetail = casesDetail.find { it.id == caseId }

    val students = listOf("Estudiante A", "Estudiante B", "Estudiante Cccccccccccccccccccccc")

    var expanded by remember { mutableStateOf(false) }
    var selectedStudent by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Caso #$caseId", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
            )
        }
    ) { paddingValues ->
        caseDetail?.let { detail ->
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
                        // Información del nombre
                        Text(
                            text = "NOMBRE:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray // Cambiado a dark grey
                        )
                        Text(
                            text = detail.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,  // Cambiado a negro
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)

                        Text(
                            text = "CORREO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.email,
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
                            text = detail.phone,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                        // Información del servicio
                        Text(
                            text = "SERVICIO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.service,
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

                Button(
                    onClick = { /* Acción de editar caso */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),  // Cambiado a negro
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Editar Caso", color = Color.White)
                }

                Button(
                    onClick = { /* Acción de descargar como PDF */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),  // Cambiado a negro
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Descargar como PDF", color = Color.White)
                }

                // Botón de "Compartir" con lista desplegable
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { expanded = !expanded },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Compartir Caso", color = Color.White)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        students.forEach { student ->
                            DropdownMenuItem(
                                text = { Text(student) },
                                onClick = {
                                    selectedStudent = student
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                if (selectedStudent.isNotEmpty()) {
                    Text(
                        text = "Caso asignado a: $selectedStudent",
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

@Preview(showBackground = true)
@Composable
fun EditarCasosAbogadosViewPreview() {
    BinaryBrainzTheme {
        EditarCasosAbogadosView(navController = rememberNavController(), caseId = "123")
    }
}
