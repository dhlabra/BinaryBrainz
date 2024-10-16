package com.example.binarybrainz.Estudiantes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.binarybrainz.Extras.UserViewModel
import com.example.binarybrainz.Navigation.EstudianteNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstudianteView(navController: NavHostController, viewModel: UserViewModel) {
    Scaffold(
        content = { innerPadding ->
            EstudianteNavigation(navController = navController, modifier = Modifier.padding(innerPadding), viewModel)
        }
    )
}
