package com.example.binarybrainz.Abogados

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.Asesoria
import com.example.binarybrainz.Extras.Cita
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun CitasPendientesScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    var citas by remember { mutableStateOf<List<Cita>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadCitaList()
        citas = viewModel.citaList.filter { it.status == "Pendiente" }
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
                    citas = citas
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
fun CitasPendientesList(navController: NavController, citas: List<Cita>) {
    LazyColumn {
        itemsIndexed(citas) { index, cita ->
            CitasPendientesItem(navController = navController, cita = cita)
            if (index < citas.size - 1) {
                Divider(thickness = 1.dp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun CitasPendientesItem(navController: NavController, cita: Cita) {
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
                text = "Caso #${cita.asesoria_id}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Ver",
                color = Color.Gray,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate("cita_detalle_view/${cita.asesoria_id}") }
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
