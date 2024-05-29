package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@OptIn(MapboxExperimental::class)
@Preview
@Composable
fun MapContainer() {
    Box(
        modifier =
            Modifier
                .padding(14.dp, 18.dp)
                .border(BorderStroke(2.dp, Color.White))
                .fillMaxWidth()
                .fillMaxHeight(),
    ) {
        val mapViewportState =
            rememberMapViewportState {
                setCameraOptions {
                    zoom(4.0)
                    pitch(0.0)
                }
            }

        MapboxContent(
            mapViewportState = mapViewportState,
            modifier =
                Modifier
                    .fillMaxSize(),
        )
    }
}