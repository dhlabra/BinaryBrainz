package com.example.binarybrainz.Clientes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.Navigation.ClienteNavigation

@Composable
fun ClienteView(navController: NavHostController, viewModel: UserViewModel) {
    Scaffold(
        topBar = {
            TopBarClientes(navController, viewModel)
        },
        content = { innerPadding ->
            ClienteNavigation(navController = navController, modifier = Modifier.padding(innerPadding), viewModel)
        }
    )
}