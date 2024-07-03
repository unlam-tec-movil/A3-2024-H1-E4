package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.PhotoCaptureViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.RegisterViewModel
import ar.edu.unlam.mobile.scaffolding.utils.ImageUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: RegisterViewModel = hiltViewModel(),
    photoCaptureViewModel: PhotoCaptureViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val bitmap by photoCaptureViewModel.bitmap.collectAsState()
    var finalImage: ByteArray? = null
    val permissionGranted by photoCaptureViewModel.permissionGranted.collectAsState()
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            photoCaptureViewModel.setPermissionGranted(isGranted)
            if (!isGranted) {
                Log.e("PhotoCapture", "Permiso Rechazado")
            }
        }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { result ->
            photoCaptureViewModel.setBitmap(result)
        }

    val uiState by viewModel.userUiState.collectAsState()
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        if (!uiState.loading) {
            navController.navigate(Routes.Home.name)
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            photoCaptureViewModel.setPermissionGranted(true)
        }
    }

    fun handleContinueButtonClick() {
        if (name == "" || lastName == "" || weight == "" || height == "" || age == "") {
            Toast.makeText(context, "Todos los datos son obligatorios!", Toast.LENGTH_SHORT).show()
            return
        }
        val user =
            User(
                1,
                name,
                lastName,
                finalImage,
                age.toInt(),
                height.toInt(),
                weight.toDouble(),
                0.0,
                0,
                0,
                0,
            )
        viewModel.setUser(user)
        navController.navigate(Routes.Home.name)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.register_title),
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        Text(
            text = stringResource(id = R.string.register_subtitle),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp),
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(id = R.string.register_name)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(stringResource(id = R.string.register_lastname)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text(stringResource(id = R.string.register_height)) },
            placeholder = { Text("Ejemplo: 180") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text(stringResource(id = R.string.register_weight)) },
            placeholder = { Text("Ejemplo: 85.5") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(stringResource(id = R.string.register_age)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
        )

        IconButton(
            onClick = { takePicture(context, permissionGranted, launcher) },
            modifier.size(40.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_solid),
                contentDescription = "Camara",
                modifier.size(30.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Foto del usuario",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp),
            )

            finalImage = ImageUtils.convertBitmapToByteArray(it)
        }

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
                text = stringResource(id = R.string.register_continue),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

fun takePicture(
    context: Context,
    permissionGranted: Boolean,
    launcher: ManagedActivityResultLauncher<Void?, Bitmap?>,
) {
    if (permissionGranted) {
        launcher.launch()
    } else {
        Toast.makeText(
            context,
            "Se requieren permisos para acceder a la cámara",
            Toast.LENGTH_LONG,
        )
    }
}
