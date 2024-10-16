package com.example.binarybrainz.Abogados

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.Navigation.AbogadoNavigation
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbogadoView(navController: NavHostController, viewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerAbogados(navController = navController, drawerState = drawerState, viewModel)
        }
    ) {

        Scaffold(
            topBar = {
                TopBarAbogados(
                    navController = navController,
                    drawerState = drawerState, // Asegúrate de que sea un DrawerState
                    viewModel = viewModel,
                    onUserIconClick = { /* Acciones al hacer click en el ícono de usuario */ }
                )
            },
            content = { innerPadding ->
                AbogadoNavigation(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    viewModel
                )
            }
        )
    }
}
