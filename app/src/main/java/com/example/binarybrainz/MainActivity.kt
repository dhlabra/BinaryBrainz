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
import com.example.binarybrainz.views.VistaPrincipal
import com.example.binarybrainz.views.VistaServicios

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
    NavHost(navController = navController, startDestination = "vista_principal") {
        composable("vista_principal") {
            VistaPrincipal(navController)
        }
        composable("vista_servicios") {
            VistaServicios()
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
