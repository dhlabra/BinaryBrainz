package com.example.binarybrainz.StudentViews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme
import com.example.binarybrainz.Estudiantes.TopBarEstudiante // Importa el nuevo TopBarEstudiante
import com.example.binarybrainz.Extras.Asesoria

@Composable
fun ApartadoCasosCompartidosView(navController: NavController, viewModel: UserViewModel) {
    var isLoading by remember { mutableStateOf(false) }

    var asesorias by remember { mutableStateOf<List<Asesoria>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        viewModel.loadAsesoriaList()
        asesorias = viewModel.asesoriaList
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopBarEstudiante(viewModel = viewModel, navController = navController, studentName = "Chapa")}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Casos compartidos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            CaseList(navController = navController, cases = asesorias)
        }
    }
}

@Composable
fun CaseList(navController: NavController, cases: List<Asesoria>) {
    LazyColumn {
        items(cases) { caseId ->
            CaseRow(navController = navController, caseId = caseId.id)
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun CaseRow(navController: NavController, caseId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Caso #$caseId", fontSize = 18.sp)
        IconButton(onClick = { navController.navigate("edit_case_view/$caseId") }) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar Caso")
        }
    }
}
