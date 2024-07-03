package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.Coordinate
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityData
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityStartButton
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityStopButton
import ar.edu.unlam.mobile.scaffolding.ui.components.MapboxContent
import ar.edu.unlam.mobile.scaffolding.ui.components.NoLocationPermissionContent
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.ActivityProgressViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.CoordinateUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.SpotifyViewModel
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ar.edu.unlam.mobile.scaffolding.ui.components.Spotify

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActivityProgressScreen(
    viewModel: ActivityProgressViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    viewModelSpotify: SpotifyViewModel = hiltViewModel(),
    userWeight: String = "",
    userId: String = "",
) {
    var coordinates by rememberSaveable {
        mutableStateOf(listOf<Coordinate>())
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    var elapsedTimeState by rememberSaveable {
        mutableLongStateOf(0L)
    }
    val showEndDialog =
        remember {
            mutableStateOf(false)
        }

    val uiState by viewModel.uiState.collectAsState()
    val caloriesState by viewModel.getCalories(userWeight.toDouble()).collectAsState()
    val speedAverageState by viewModel.speedAverageState.collectAsState()
    val distanceState by viewModel.distanceState.collectAsState()
    val context = LocalContext.current
    val elapsedTime by viewModel.elapsedTimeState.collectAsState()

    val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    elapsedTimeState = elapsedTime

    val rememberPermissionState =
        rememberPermissionState(permission = fineLocationPermission)

    LaunchedEffect(key1 = true) {
        rememberPermissionState.launchPermissionRequest()
        scaffoldState.bottomSheetState.expand()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Actividad") },
            colors =
                topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            actions = {
                Spotify(viewModelSpotify)
                IconButton(onClick = {
                    showEndDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            },
        )
    }) {
        if (showEndDialog.value) {
            AlertDialog(
                onDismissRequest = { showEndDialog.value = false },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.xmark_solid),
                        contentDescription = "Exit",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp),
                    )
                },
                title = {
                    Text(text = "Abandonar Actividad")
                },
                text = { Text(text = "¿Está seguro que desea abandonar la actividad? No se guardará.") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.cancel()
                        navController.navigate(Routes.Home.name)
                    }) {
                        Text(text = "Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDialog.value = false }) {
                        Text(text = "Cancelar")
                    }
                },
            )
        }

        when (val coordinateUIState = uiState.coordinateUIState) {
            is CoordinateUIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            is CoordinateUIState.Success -> {
                coordinates = coordinateUIState.coordinateList
            }

            is CoordinateUIState.Error -> {
                Toast.makeText(
                    context,
                    stringResource(id = R.string.error_location_message),
                    Toast.LENGTH_LONG,
                )
                    .show()
            }
        }

        if (rememberPermissionState.status.isGranted) {
            MapboxContent(
                locationCoordinates = coordinates,
                modifier = Modifier.fillMaxSize(),
            )

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 80.dp,
                sheetContent = {
                    BottomSheetContent(
                        elapsedTimeState,
                        speedAverageState,
                        distanceState,
                        caloriesState,
                        onActivityStop = { viewModel.pause() },
                        onActivityStart = { viewModel.start() },
                        onActivityEnd = {
                            viewModel.stop(if (userId != "") userId.toLong() else 1)
                            navController.navigate(Routes.Home.name)
                        },
                    )
                },
            ) {}
        } else {
            NoLocationPermissionContent()
        }
    }
}

@Composable
private fun BottomSheetContent(
    elapsedTimeState: Long,
    speedState: Float,
    distanceState: Float,
    caloriesState: Double,
    onActivityStart: () -> Unit,
    onActivityStop: () -> Unit,
    onActivityEnd: () -> Unit,
) {
    var running by
        rememberSaveable {
            mutableStateOf(false)
        }

    val swapRunningState = { running = !running }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp, 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(12.dp))
        Row(
            modifier =
                Modifier
                    .width(200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = (!running && elapsedTimeState > 0),
                enter =
                    scaleIn(
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                        initialScale = 0f,
                        transformOrigin = TransformOrigin.Center,
                    ),
                exit = scaleOut(),
            ) {
                Row {
                    ActivityStopButton(
                        running = running,
                        onActivityEnd = onActivityEnd,
                        swapRunningState = swapRunningState,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
            ActivityStartButton(
                running = running,
                onActivityStop = onActivityStop,
                onActivityStart = onActivityStart,
                swapRunningState = swapRunningState,
                modifier = Modifier.weight(1f),
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = DateTimeUtils.formatTime(elapsedTimeState),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 64.sp,
        )
        Text(
            text = "Tiempo",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .padding(top = 32.dp, bottom = 24.dp)
                .fillMaxWidth(),
    ) {
        ActivityData(
            "Velocidad (Km/h)",
            "%.2f".format(speedState),
            Modifier.weight(1f),
        )
        ActivityData(
            "Distancia (Km)",
            "%.2f".format(distanceState),
            Modifier.weight(1f),
        )
        Log.i("CALORIAS", "CALORIAS STATE = $caloriesState")
        ActivityData(
            "Calorias",
            "%.2f".format(caloriesState),
            Modifier.weight(1f),
        )
    }
}
