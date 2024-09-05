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
fun ApartadoCasosCompartidos(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Casos Compartidos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción de perfil de usuario */ }) {
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
            CaseList(cases = listOf("Caso #123", "Caso #333", "Caso #475", "Caso #758"))
        }
    }
}

@Composable
fun CaseList(cases: List<String>) {
    LazyColumn {
        items(cases) { case ->
            CaseRow(case)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun CaseRow(case: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = case, fontSize = 18.sp)
        IconButton(onClick = { /* Acción de editar caso */ }) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar Caso")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApartadoCasosCompartidosPreview() {
    BinaryBrainzTheme {
        ApartadoCasosCompartidos(navController = rememberNavController())
    }
}
