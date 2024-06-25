package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActivityProgressScreen(
    viewModel: ActivityProgressViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var coordinates: List<Coordinate> = listOf()
    val speedState by viewModel.speedState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current

    val elapsedTime by viewModel.eleapsedTimeState.collectAsState()
    var elapsedTimeState by remember {
        mutableLongStateOf(0L)
    }
    var isRunning by remember {
        mutableStateOf(false)
    }
    elapsedTimeState = elapsedTime

    val mapViewportState =
        rememberMapViewportState {
            setCameraOptions {
                zoom(0.3)
                pitch(0.0)
            }
        }

    val permissions =
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

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
                    viewModel.stop()
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
                    "Ocurrió un error al obtener la ubicación",
                    Toast.LENGTH_LONG,
                )
                    .show()
            }
        }

        if (rememberMultiplePermissionsState.permissions[0].status.isGranted ||
            rememberMultiplePermissionsState.permissions[1].status.isGranted
        ) {
            MapboxContent(
                mapViewportState = mapViewportState,
                locationCoordinates = coordinates,
                modifier = Modifier.fillMaxSize(),
            )
        }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 80.dp,
            sheetContent = {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp, 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.padding(12.dp))
                    Button(
                        onClick = {
                            if (isRunning) {
                                viewModel.pause()
                                isRunning = false
                            } else {
                                viewModel.start()
                                isRunning = true
                            }
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
                                if (isRunning) {
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
                        "12.6",
                        Modifier.weight(1f),
                    )
                    ActivityData(
                        "Calorias",
                        "196",
                        Modifier.weight(1f),
                    )
                }
            },
        ) {
        }
    }
}
