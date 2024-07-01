package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.RegisterViewModel

@Preview
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.userUiState.collectAsState()
    LaunchedEffect(key1 = true) {
        if (!uiState.loading) {
            navController.navigate(Routes.Home.name)
        }
    }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    fun handleContinueButtonClick() {
        if (name == "" || lastName == "" || weight == "" || height == "" || age == "") {
            Toast.makeText(context, "Todos los datos son obligatorios!", Toast.LENGTH_SHORT).show()
            return
        }
        val user = User(1, name, lastName, age.toInt(), height.toInt(), weight.toDouble(), 0.0, 0, 0, 0)
        viewModel.setUser(user)
        navController.navigate(Routes.Home.name)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Título
        Text(
            text = "Ingresa tus datos",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        // Input para el nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Altura (cm)") },
            placeholder = { Text("Ejemplo: 180") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )
        // Input para el peso
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Peso (Kg)") },
            placeholder = { Text("Ejemplo: 85.5") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        // Input para la edad
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
        )

        // Botón continuar
        OutlinedButton(
            onClick = { handleContinueButtonClick() },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
            colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colorScheme.primary),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
        ) {
            Text(
                text = "Continuar",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}
