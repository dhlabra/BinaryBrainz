package com.example.binarybrainz.ClientViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@Composable
fun CitaClienteUpdatesView(navController: NavHostController, viewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Parte superior: Rectángulo grande
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Información del Caso", fontSize = 24.sp, color = Color.Black)
        }

        // Parte inferior: Dos cuadrados pequeños
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cuadrado izquierdo
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Detalles de la Cita", fontSize = 18.sp, color = Color.White)
            }

            // Cuadrado derecho
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Gray, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Actualizaciones del Caso", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}


