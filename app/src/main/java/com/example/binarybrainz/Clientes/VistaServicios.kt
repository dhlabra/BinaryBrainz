package com.example.binarybrainz.Clientes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaServicios(navController: NavController, viewModel: UserViewModel, modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Necesito Ayuda") },
                icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                modifier = modifier.padding(8.dp),
                containerColor = Color.LightGray,
                onClick = { navController.navigate("generar_casos_clientes_view") } // Navegación a la vista para generar casos
            )
        },
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.clinicapenal),
                        contentDescription = "Logotipo de la Clínica Penal",
                        modifier = Modifier
                            .size(100.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate("login_view") }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(-60.dp), // Ajuste de espacio entre los elementos
        ) {
            // Tarjetas de servicios disponibles con navegación
            ImageCardVertical(
                imageId = R.drawable.violenciadomestica,
                description = "Violencia Doméstica",
                onClick = { navController.navigate("mas_informacion_view/violencia_domestica") }
            )
            ImageCardVertical(
                imageId = R.drawable.sentenciadedivorcio,
                description = "Sentencia de Divorcio",
                onClick = { navController.navigate("mas_informacion_view/sentencia_divorcio") }
            )
            ImageCardVertical(
                imageId = R.drawable.testamento,
                description = "Testamento",
                onClick = { navController.navigate("mas_informacion_view/testamento") }
            )
            ImageCardVertical(
                imageId = R.drawable.pensionalimenticia,
                description = "Pensión Alimenticia",
                onClick = { navController.navigate("mas_informacion_view/pension_alimenticia") }
            )
            ImageCardVertical(
                imageId = R.drawable.acosolaboral,
                description = "Acoso Laboral",
                onClick = { navController.navigate("mas_informacion_view/acosolaboral") }
            )
            ImageCardVertical(
                imageId = R.drawable.adopcion,
                description = "Adopción",
                onClick = { navController.navigate("mas_informacion_view/adopcion") }
            )
            ImageCardVertical(
                imageId = R.drawable.contratosarrendamientos,
                description = "Contratos de Arrendamiento",
                onClick = { navController.navigate("mas_informacion_view/contratos_arrendamientos") }
            )
            ImageCardVertical(
                imageId = R.drawable.custodiamenores,
                description = "Custodia de Menores",
                onClick = { navController.navigate("mas_informacion_view/custodia_menores") }
            )
            ImageCardVertical(
                imageId = R.drawable.despidoinjustificado,
                description = "Despido Injustificado",
                onClick = { navController.navigate("mas_informacion_view/despido_injustificado") }
            )
            ImageCardVertical(
                imageId = R.drawable.herenciasucesion,
                description = "Herencia y Sucesión",
                onClick = { navController.navigate("mas_informacion_view/herencia_sucesion") }
            )
        }
    }
}

@Composable
fun ImageCardVertical(imageId: Int, description: String, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(16.dp)
            .safeDrawingPadding(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = description,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = description
                )
                Button(
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    onClick = onClick
                ) {
                    Text(text = "Más información")
                }

            }
        }
    }
}