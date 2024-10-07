package com.example.binarybrainz.Extras

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.binarybrainz.Navigation.AbogadoNavigation
import com.example.binarybrainz.Navigation.GeneralNavigation
import com.example.binarybrainz.components.DrawerAbogados
import com.example.binarybrainz.components.TopBarAbogados

@Composable
fun LoginInject(navController:NavHostController, viewModel: UserViewModel) {
    Scaffold(
        topBar = {},
        content = { innerPadding ->
            GeneralNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                viewModel
            )
        }
    )
}
