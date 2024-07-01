package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import ar.edu.unlam.mobile.scaffolding.ui.components.MapboxContent
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.ActivityProgressViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.CoordinateUIState
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActivityProgressScreen(
    viewModel: ActivityProgressViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
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

    val caloriesState by viewModel.getCalories(userWeight.toDouble()).collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val speedAverageState by viewModel.speedAverageState.collectAsState()
    val distanceState by viewModel.distanceState.collectAsState()
    val context = LocalContext.current
    val elapsedTime by viewModel.eleapsedTimeState.collectAsState()

    val permissions =
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

    elapsedTimeState = elapsedTime

    val rememberMultiplePermissionsState =
        rememberMultiplePermissionsState(permissions = permissions)

    LaunchedEffect(key1 = true) {
        rememberMultiplePermissionsState.launchMultiplePermissionRequest()
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
                IconButton(onClick = {
                    viewModel.stop(userId.toLong())
                    navController.navigate(Routes.Home.name)
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
                // TODO: llevar texto a strings.xml
                Toast.makeText(
                    context,
                    stringResource(id = R.string.error_location_message),
                    Toast.LENGTH_LONG,
                )
                    .show()
            }
        }

        if (rememberMultiplePermissionsState.permissions.any { it.status.isGranted }
        ) {
            Log.i("Location Coordinates", "Activity Screen coord= $coordinates")
            MapboxContent(
                locationCoordinates = coordinates,
                modifier = Modifier.fillMaxSize(),
            )
        }
        // Log.i("PESO DEL USUARIO", "PESO RECIBIDO = $userWeight")
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
                    onActivityFinish = { viewModel.stop(userId.toLong()) }
                )
            },
        ) {
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
    onActivityFinish: () -> Unit,
) {
    var running by
        rememberSaveable {
            mutableStateOf(false)
        }
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(4.dp, 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(12.dp))
        if(running) {
            Button(
                onClick = {
                    if (running) {
                        onActivityStop()
                    } else {
                        onActivityStart()
                    }
                    running = !running
                },
                modifier = Modifier.size(90.dp),
                shape = CircleShape,
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(35, 79, 113, 255),
                ),
            ) {
                Image(
                    painter =
                    if (running) {
                        painterResource(
                            id = R.drawable.baseline_pause_24,
                        )
                    } else {
                        painterResource(id = R.drawable.baseline_play_arrow_24)
                    },
                    contentDescription = null,
                    modifier =
                    Modifier
                        .size(64.dp),
                )
            }
        } else {
            Row {
                Button(
                    onClick = {
                        if (running) {
                            onActivityStop()
                        } else {
                            onActivityStart()
                        }
                        running = !running
                    },
                    modifier = Modifier.size(90.dp),
                    shape = CircleShape,
                    colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color(35, 79, 113, 255),
                    ),
                ) {
                    Image(
                        painter =
                        if (running) {
                            painterResource(
                                id = R.drawable.baseline_pause_24,
                            )
                        } else {
                            painterResource(id = R.drawable.baseline_play_arrow_24)
                        },
                        contentDescription = null,
                        modifier =
                        Modifier
                            .size(64.dp),
                    )
                }
            Button(
                onClick = {
                    running = !running
                    onActivityFinish()
                },
                modifier = Modifier.size(90.dp),
                shape = CircleShape,
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(35, 79, 113, 255),
                ),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    tint = Color.White,
                    contentDescription = null,
                    modifier =
                    Modifier
                        .size(64.dp),
                )
            }
            }
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
