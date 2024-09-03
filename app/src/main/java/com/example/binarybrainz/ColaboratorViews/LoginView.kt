package com.example.binarybrainz.ColaboratorViews

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.binarybrainz.R
import com.example.binarybrainz.UserViews.VistaServicios
import com.example.binarybrainz.ui.theme.BinaryBrainzTheme

@Composable
fun LoginView() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.clinicapenal),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
        )
        EditNumberField(
            label = R.string.user,
            leadingIcon = Icons.Filled.AccountCircle,
            value = username,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions.Default.copy(
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
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { /* TODO: Acción para "Más información" */ },) {
            Text(text = "Inicar Sesión")
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
    isPasswordVisible : Boolean,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        onValueChange = onValueChange,
        modifier = modifier,
        label = {Text(stringResource(label))},
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions
    )
}

@Preview(showBackground = true)
@Composable
fun VistaServiciosPreview() {
    BinaryBrainzTheme {
        LoginView()
    }
}