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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.R
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

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
                navController.navigate("vista_servicios")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Comenzar")
        }

        TextButton(onClick = { navController.navigate("login_view") }) {
            Text("Soy Colaborador")
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