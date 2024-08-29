package com.example.binarybrainz.UserViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.ImageCard
import com.example.binarybrainz.R
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaServicios(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
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
                            .size(48.dp)
                            .padding(2.dp)
                    )
                },
                actions = {
                    TextButton(onClick = { /* TODO: Acción para "Servicios" */ }) {
                        Text("Servicios")
                    }
                    TextButton(onClick = { navController.navigate("necesito_ayuda_view") }) {
                        Text("Necesito Ayuda")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            ImageCard(imageId = R.drawable.clinicapenal, description = "Violencia Doméstica")
            ImageCard(imageId = R.drawable.clinicapenal, description = "Sentencia de Divorcio")
            ImageCard(imageId = R.drawable.clinicapenal, description = "Testamento")
            ImageCard(imageId = R.drawable.clinicapenal, description = "Pensión Alimenticia")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaServiciosPreview() {
    BinaryBrainzTheme {
        VistaServicios(navController = rememberNavController())
    }
}