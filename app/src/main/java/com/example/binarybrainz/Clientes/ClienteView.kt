package com.example.binarybrainz.Clientes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.Navigation.ClienteNavigation
import com.example.binarybrainz.Navigation.EstudianteNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteView(navController: NavHostController, viewModel: UserViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nombre Cliente",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                actions = {
                    IconButton(onClick = {
                        // Redirige al login cuando se presiona el ícono de usuario
                        viewModel.signOut()
                    }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Perfil de Usuario")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        },
        content = { innerPadding ->
            ClienteNavigation(navController = navController, modifier = Modifier.padding(innerPadding), viewModel)
        }
    )
}