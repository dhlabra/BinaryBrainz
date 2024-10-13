package com.example.binarybrainz.Abogados

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.Clientes.ClienteView
import com.example.binarybrainz.Extras.EditNumberField
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.R
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun SignUpView(navController: NavController, viewModel: UserViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .padding(paddingValues)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.clinicapenal),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
            )
            EditNumberField(
                label = R.string.correo,
                leadingIcon = Icons.Filled.Email,
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.password,
                leadingIcon = Icons.Filled.Lock,
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = false,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.nombre,
                leadingIcon = Icons.Filled.Face,
                value = nombre,
                onValueChange = { nombre = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.apellido,
                leadingIcon = Icons.Filled.Face,
                value = apellido,
                onValueChange = { apellido = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.celular,
                leadingIcon = Icons.Filled.Phone,
                value = celular,
                onValueChange = { celular = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            rol = "cliente"

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra un mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage
                )
            }

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.signUp(email, password)
                          },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Next")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpView2(navController: NavController, viewModel: UserViewModel) {

    var nombre by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    val navController = rememberNavController()

    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .padding(paddingValues)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.Center
        ) {
            EditNumberField(
                label = R.string.nombre,
                leadingIcon = Icons.Filled.Face,
                value = nombre,
                onValueChange = { nombre = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.apellido,
                leadingIcon = Icons.Filled.Face,
                value = apellido,
                onValueChange = { apellido = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            EditNumberField(
                label = R.string.celular,
                leadingIcon = Icons.Filled.Phone,
                value = celular,
                onValueChange = { celular = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                isPasswordVisible = true,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )

            rol = "cliente"

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra un mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage
                )
            }

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.setUser(rol, nombre, apellido, celular)
                    viewModel.signOut()
                    navController.navigate("login_view")
                },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Sign Up")
                }
            }
        }
    }
}
