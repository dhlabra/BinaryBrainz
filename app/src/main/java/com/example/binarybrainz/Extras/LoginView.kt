package com.example.binarybrainz.Extras

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.binarybrainz.R

@Composable
fun LoginView(navController: NavController, viewModel: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo de la Clínica Penal como botón que redirige a VistaServicios.kt
        Image(
            painter = painterResource(R.drawable.clinicapenal),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .clickable { navController.navigate("vista_servicios") } // Redirige al menu principal al hacer clic en el logo
        )

        // Campo de email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.correo)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            singleLine = true,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        // Campo de contraseña con visibilidad toggle
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
            },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.ThumbUp else Icons.Filled.Edit
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            singleLine = true,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        // Botón de Sign In
        Button(
            onClick = { viewModel.signIn(email, password) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                )
            } else {
                Text("Sign In")
            }
        }

        // Botón para ir a Menu Abogado
        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onClick = { navController.navigate("menu_casos_pendientes_view") }
        ) {
            Text(text = "Menu Abogado")
        }

        // Botón adicional para "Menu Estudiantes"
        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onClick = { navController.navigate("apartado_casos_compartidos_view") }
        ) {
            Text(text = "Menu Estudiantes")
        }

        // Botón para regresar a VistaServicios
        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onClick = { navController.navigate("vista_servicios") }
        ) {
            Text(text = "Regresar al Menu Principal")
        }

        // Mensaje de error
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    leadingIcon: ImageVector,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(stringResource(label)) },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions
    )
}
