package com.example.binarybrainz.Abogados

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun CitasPendientesScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                    // Aquí se redirige al login cuando se presiona el icono de usuario
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

                CitasPendientesTitleSection()

                Spacer(modifier = Modifier.height(24.dp))

                CitasPendientesFilterSection()

                Spacer(modifier = Modifier.height(24.dp))

                CitasPendientesList(
                    navController = navController,
                    citas = listOf(
                        CitaPendiente("123", "2023-10-15", "10:00 AM"),
                        CitaPendiente("333", "2023-10-16", "11:00 AM"),
                        CitaPendiente("475", "2023-10-17", "02:00 PM"),
                        CitaPendiente("758", "2023-10-18", "04:00 PM")
                    )
                )
            }
        }
    }
}

@Composable
fun CitasPendientesTitleSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Citas Pendientes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CitasPendientesFilterSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier
                    .clickable { /* Acción del filtro */ }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "FILTRAR POR",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
    }
}

// Model class to hold pending case data
data class CitaPendiente(val caseId: String, val date: String, val time: String)

@Composable
fun CitasPendientesList(navController: NavController, citas: List<CitaPendiente>) {
    LazyColumn {
        items(citas) { cita ->
            CitasPendientesItem(navController = navController, cita = cita)
            Divider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun CitasPendientesItem(navController: NavController, cita: CitaPendiente) {
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
            Text(
                text = "Caso #${cita.caseId}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Ver",
                color = Color.Gray,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate("cita_detalle_view/${cita.caseId}") }
            )
        }

        // Display date and time in smaller gray text
        Text(
            text = "Fecha: ${cita.date} | Hora: ${cita.time}",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
