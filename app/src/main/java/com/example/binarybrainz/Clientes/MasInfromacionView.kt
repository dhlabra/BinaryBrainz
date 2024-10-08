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
                "La violencia doméstica es un patrón de comportamiento utilizado para establecer poder y control sobre otra persona en el contexto de una relación doméstica. Puede tomar muchas formas, incluyendo abuso físico, emocional, sexual, psicológico y económico." ,
                listOf("Insultos constantes y humillaciones.", "Golpear o empujar a la pareja durante una discusión.", "Controlar el dinero de la pareja.", "Amenazar con hacerle daño a la víctima o a sus seres queridos si no obedece.")
            )
        }
        "sentencia_divorcio" -> {
            Quadruple(
                R.drawable.sentenciadedivorcio,
                "Sentencia de Divorcio",
                "Una sentencia de divorcio es un fallo judicial que pone fin legalmente a un matrimonio. Es el documento final emitido por un juez que establece los términos bajo los cuales los cónyuges quedan legalmente divorciados.",
                listOf("Asignación de la custodia compartida de los hijos", "El padre se obliga a pagar una pensión alimenticia mensual para el mantenimiento de los hijos.", "Una madre obtiene la custodia física.")
            )
        }
        "testamento" -> {
            Quadruple(
                R.drawable.testamento,
                "Testamento",
                "Un testamento es un documento legal que expresa las últimas voluntades de una persona con respecto a la distribución de sus bienes y propiedades tras su fallecimiento. También puede incluir otras disposiciones importantes, como la designación de tutores para los hijos menores y la instrucción sobre el manejo de deudas pendientes.",
                listOf("Dejar una propiedad a un hijo o familiar", "Asignar una suma de dinero a un beneficiario", "Distribuir bienes personales")
            )
        }
        "pension_alimenticia" -> {
            Quadruple(
                R.drawable.pensionalimenticia,
                "Pensión Alimenticia",
                "La pensión alimenticia es una obligación legal que impone a una persona, generalmente un padre o madre, para contribuir al bienestar financiero de sus hijos o, en algunos casos, de su expareja. Esta pensión está destinada a cubrir necesidades básicas como alimentación, educación, vivienda, salud, y otros gastos esenciales.",
                listOf("Pago mensual para alimentos y vivienda", "Contribución a la educación privada o universitaria", "Cobertura de gastos médicos")
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
