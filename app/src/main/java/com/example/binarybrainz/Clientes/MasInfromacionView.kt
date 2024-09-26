package com.example.binarybrainz.UserViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.R
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasInformacionView(navController: NavController, servicioDescription: String?) {
    // Cambiando a una estructura que devuelve cuatro valores
    val (imageId, title, description, examples) = when (servicioDescription) {
        "violencia_domestica" -> {
            Quadruple(
                R.drawable.violenciadomestica, // Asegúrate de tener la imagen correcta
                "Violencia Doméstica",
                "Información detallada sobre violencia doméstica...",
                listOf("Ejemplo 1", "Ejemplo 2", "Ejemplo 3")
            )
        }
        "sentencia_divorcio" -> {
            Quadruple(
                R.drawable.sentenciadedivorcio,
                "Sentencia de Divorcio",
                "Información detallada sobre sentencia de divorcio...",
                listOf("Ejemplo A", "Ejemplo B", "Ejemplo C")
            )
        }
        "testamento" -> {
            Quadruple(
                R.drawable.testamento,
                "Testamento",
                "Información detallada sobre testamentos...",
                listOf("Ejemplo X", "Ejemplo Y", "Ejemplo Z")
            )
        }
        "pension_alimenticia" -> {
            Quadruple(
                R.drawable.pensionalimenticia,
                "Pensión Alimenticia",
                "Información detallada sobre pensión alimenticia...",
                listOf("Ejemplo 1", "Ejemplo 2", "Ejemplo 3")
            )
        }
        else -> {
            Quadruple(
                R.drawable.clinicapenal,
                "Categoría Desconocida",
                "No se pudo cargar la información.",
                emptyList()
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
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
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ejemplos:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            examples.forEach { example ->
                Text(text = example, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Función auxiliar para manejar cuatro valores en la estructura de retorno
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

@Preview(showBackground = true)
@Composable
fun MasInformacionViewPreview() {
    BinaryBrainzTheme {
        MasInformacionView(navController = rememberNavController(), servicioDescription = "violencia_domestica")
    }
}
