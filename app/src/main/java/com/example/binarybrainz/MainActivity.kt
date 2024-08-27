package com.example.binarybrainz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BinaryBrainzTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "vistaPrincipal") {
        composable("vistaPrincipal") {
            VistaPrincipal(navController)
        }
        composable("vistaServicios") {
            VistaServicios()
        }
    }
}

@Composable
fun VistaPrincipal(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del logotipo de la aplicación
        Image(
            painter = painterResource(id = R.drawable.clinicapenal),
            contentDescription = "Logotipo de la Aplicación",
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de bienvenida
        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Comenzar"
        Button(
            onClick = {
                navController.navigate("vistaServicios")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Comenzar")
        }
    }
}

@Composable
fun VistaServicios(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logotipo en la parte superior izquierda
                Image(
                    painter = painterResource(id = R.drawable.clinicapenal),
                    contentDescription = "Logotipo de la Clínica Penal",
                    modifier = Modifier.size(48.dp)
                )

                // Botones en la parte superior derecha
                Row {
                    TextButton(onClick = { /* TODO: Acción para "Nuestros Servicios" */ }) {
                        Text("Nuestros Servicios")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { /* TODO: Acción para "Login" */ }) {
                        Text("Login")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { /* TODO: Acción para "Necesito Ayuda" */ }) {
                        Text("Necesito Ayuda")
                    }
                }
            }
        }
    ) { paddingValues ->
        // Contenido principal con las imágenes y botones
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Fila de imágenes superiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ImageCard(imageId = R.drawable.clinicapenal, description = "Violencia Doméstica")
                ImageCard(imageId = R.drawable.clinicapenal, description = "Sentencia de Divorcio")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila de imágenes inferiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ImageCard(imageId = R.drawable.clinicapenal, description = "Testamento")
                ImageCard(imageId = R.drawable.clinicapenal, description = "Pensión Alimenticia")
            }
        }
    }
}

@Composable
fun ImageCard(imageId: Int, description: String) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = description,
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp)
        )
        Button(onClick = { /* TODO: Acción para "Más información" */ }) {
            Text(text = "Más información")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPrincipalPreview() {
    BinaryBrainzTheme {
        VistaPrincipal(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun VistaServiciosPreview() {
    BinaryBrainzTheme {
        VistaServicios()
    }
}
