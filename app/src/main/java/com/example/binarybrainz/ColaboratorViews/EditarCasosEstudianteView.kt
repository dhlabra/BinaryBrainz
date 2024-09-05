package com.example.binarybrainz.StudentViews

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun EditarCasosEstudiantes(navController: NavController, caseId: String) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "NOMBRE: Fernando González Cárdenas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Text(
                text = "CORREO: Fgzz01@outlook.com    TELEFONO: 8124013672",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "IDENTIFICACIÓN OFICIAL:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )

            }
            Divider(color = Color.Gray, thickness = 1.dp)
            Text(
                text = "SERVICIO: Testamento",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Text(
                text = "BREVE DESCRIPCIÓN: Mi padre murió y quiero recuperar su herencia pero no se cómo, quisiera ayuda. Gracias.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditarCasosEstudiantesPreview() {
    BinaryBrainzTheme {
        EditarCasosEstudiantes(navController = rememberNavController(), caseId = "123")
    }
}
