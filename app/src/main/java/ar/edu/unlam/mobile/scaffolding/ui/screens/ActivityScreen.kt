package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityProgress
import ar.edu.unlam.mobile.scaffolding.ui.components.MapboxContent
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.ActivityViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.CoordinateUIState
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@OptIn(ExperimentalMaterial3Api::class, MapboxExperimental::class)
@Preview
@Composable
fun ActivityScreen(viewModel: ActivityViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (val coordinateUIState = uiState.coordinateUIState) {
        is CoordinateUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }

        is CoordinateUIState.Success -> {
            MapLoco(coordinates = coordinateUIState.coordinateList)
        }

        is CoordinateUIState.Error -> {
            // Error
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, MapboxExperimental::class)
@Composable
fun MapLoco(coordinates: List<Coordinate>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val sheetState = rememberModalBottomSheetState()
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = {
                    isSheetOpen = true
                },
            ) {
                Text(text = "Open sheet")
            }
        }
        if (isSheetOpen) {
            val mapViewportState =
                rememberMapViewportState {
                    setCameraOptions {
                        zoom(0.3)
                        pitch(0.0)
                    }
                }
            MapboxContent(
                mapViewportState = mapViewportState,
                locationCoordinates = coordinates,
                modifier = Modifier.fillMaxSize(),
            )
            val heightSheet by rememberSaveable {
                mutableIntStateOf(690)
            }
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { isSheetOpen = false },
                //                modifier = Modifier.background(color = Color.Transparent),
            ) {
                Column(
                    modifier =
                        Modifier
                            .height(heightSheet.dp)
                            .fillMaxWidth(),
                ) {
                    ActivityProgress() // prevFun = { heigthSheet = 140 }
                }
            }
        }
    }
}
