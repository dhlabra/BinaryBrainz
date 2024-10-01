package com.example.binarybrainz.StudentViews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApartadoCasosCompartidosView(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nombre Estudiante",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Redirige al login cuando se presiona el ícono de usuario
                        navController.navigate("login_view")
                    }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Perfil de Usuario")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
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
            CaseList(navController = navController, cases = listOf("123", "333", "475", "758"))
        }
    }
}

@Composable
fun CaseList(navController: NavController, cases: List<String>) {
    LazyColumn {
        items(cases) { caseId ->
            CaseRow(navController = navController, caseId = caseId)
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun CaseRow(navController: NavController, caseId: String) {
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

@Preview(showBackground = true)
@Composable
fun ApartadoCasosCompartidosViewPreview() {
    BinaryBrainzTheme {
        ApartadoCasosCompartidosView(navController = rememberNavController())
    }
}
