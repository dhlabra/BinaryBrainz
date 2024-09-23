package com.example.binarybrainz.StudentViews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

data class CaseDetail(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val service: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarCasosEstudiantesView(navController: NavController, caseId: String) {
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
                actions = {
                    IconButton(onClick = { /* Acción de perfil de usuario */ }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Perfil de Usuario")
                    }
                }
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
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = detail.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                        // Información del correo
                        Text(
                            text = "CORREO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.email,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                        // Información del teléfono
                        Text(
                            text = "TELÉFONO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.phone,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                        // Información del servicio
                        Text(
                            text = "SERVICIO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.service,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                        // Información de la identificación oficial
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "IDENTIFICACIÓN OFICIAL:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            IconButton(onClick = { /* Acción para mostrar identificación */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Ver Identificación",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Divider(color = Color.Gray, thickness = 0.5.dp)
                        // Información de la descripción
                        Text(
                            text = "BREVE DESCRIPCIÓN:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = detail.description,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                // Botones de acción
                Button(
                    onClick = { /* Acción de editar caso */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4267B2)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Editar Caso")
                }

                Button(
                    onClick = { /* Acción de descargar como PDF */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4267B2)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Descargar como PDF")
                }

                Button(
                    onClick = { /* Acción de compartir */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4267B2)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Compartir")
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
fun EditarCasosEstudiantesViewPreview() {
    BinaryBrainzTheme {
        EditarCasosEstudiantesView(navController = rememberNavController(), caseId = "123")
    }
}
