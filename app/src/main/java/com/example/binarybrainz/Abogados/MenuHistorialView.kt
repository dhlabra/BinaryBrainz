package com.example.binarybrainz.Abogados

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import com.example.binarybrainz.Extras.User
import com.example.binarybrainz.StudentViews.CaseRow
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun HistorialScreen(navController: NavController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    var asesorias by remember { mutableStateOf<List<Asesoria>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadAsesoriaList()
        asesorias = viewModel.asesoriaList.filter { it.status == "Finalizado" }
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

                HistorialTitleSection()

                Spacer(modifier = Modifier.height(24.dp))

                HistorialFilterSection()

                Spacer(modifier = Modifier.height(24.dp))

                HistorialCasesList(navController = navController, cases = asesorias)
            }
        }
    }
}

@Composable
fun HistorialTitleSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Historial",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HistorialFilterSection() {
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
                // Texto en lugar de icono
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
    }
}

@Composable
fun HistorialCasesList(navController: NavController, cases: List<Asesoria>) {
    LazyColumn {
        itemsIndexed(cases) { index, case ->
            HistorialCaseItem(navController = navController, caseId = case.id.toString())
            if (index < cases.size - 1) {
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun HistorialCaseItem(caseId: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Caso #$caseId", fontSize = 18.sp)
        IconButton(onClick = { navController.navigate("editar_casos_abogados/$caseId") }) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar Caso")
        }
    }
}