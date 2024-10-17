package com.example.binarybrainz.ClientViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.binarybrainz.Extras.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitaClienteUpdatesView(navController: NavHostController, viewModel: UserViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Progreso del Caso", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray // Fondo del TopBar
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sección 1: Información del Cliente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .verticalScroll(rememberScrollState()), // Scroll individual
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Título: Información del Cliente
                    Text(
                        text = "Información del Cliente",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black, // Título en negro
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Nombre
                    Text(
                        text = "Nombre",
                        fontSize = 18.sp,
                        color = Color.Black, // Título en negro
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "David",
                        fontSize = 18.sp,
                        color = Color.White // Texto en blanco
                    )

                    // Apellido
                    Text(
                        text = "Apellido",
                        fontSize = 18.sp,
                        color = Color.Black, // Título en negro
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Labra",
                        fontSize = 18.sp,
                        color = Color.White // Texto en blanco
                    )

                    // Número
                    Text(
                        text = "Número",
                        fontSize = 18.sp,
                        color = Color.Black, // Título en negro
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "123 456 7890",
                        fontSize = 18.sp,
                        color = Color.White // Texto en blanco
                    )



                }
            }

            // Sección 2: Detalles de la Cita
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .verticalScroll(rememberScrollState()), // Scroll individual
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Título: Detalles de la Cita
                    Text(
                        text = "Descripción del Caso",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black, // Título en negro
                        modifier = Modifier.padding(bottom = 8.dp)
                    )


                    Text(
                        text = "Este caso trata sobre la herencia de un familiar fallecido.",
                        fontSize = 18.sp,
                        color = Color.White // Texto en blanco
                    )
                }
            }

            // Sección 3: Actualizaciones del Caso
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .verticalScroll(rememberScrollState()), // Scroll individual
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Título: Actualizaciones del Caso
                    Text(
                        text = "Actualizaciones del Caso",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black, // Título en negro
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Información de Actualización (Placeholder)
                    Text(
                        text = "No hay actualizaciones recientes.",
                        fontSize = 18.sp,
                        color = Color.White // Texto en blanco
                    )
                }
            }
        }
    }
}

