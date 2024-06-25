package ar.edu.unlam.mobile.scaffolding.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.Coordinate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mapbox.common.MapboxOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

@SuppressLint("MutableCollectionMutableState")
@OptIn(MapboxExperimental::class, ExperimentalPermissionsApi::class)
@Composable
fun MapboxContent(
    mapViewportState: MapViewportState,
    locationCoordinates: List<Coordinate>,
    modifier: Modifier = Modifier,
) {
    MapboxOptions.accessToken = stringResource(id = R.string.mapbox_access_token)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        MapboxMap(
            modifier = modifier,
            mapViewportState = mapViewportState,
        ) {
            MapEffect(key1 = true) { mapView ->
                mapView.location.updateSettings {
                    locationPuck = createDefault2DPuck(withBearing = true)
                    puckBearingEnabled = true
                    puckBearing = PuckBearing.HEADING
                    enabled = true
                }
            }
            mapViewportState.transitionToFollowPuckState()
            Log.i("COORDINATES MAP CONTENT", "COORDENADAS: $locationCoordinates")
            if (locationCoordinates.size > 1) {
                val points =
                    locationCoordinates.map { coordinate ->
                        coordinate.toPoint()
                    }
                Log.i("COORDINATES MAP CONTENT", "COORDENADAS: $locationCoordinates")
                PolylineAnnotation(
                    points = points,
                    lineColorString = "#4dc6d1",
                    lineGapWidth = 0.0,
                    lineOpacity = 15.5,
                    lineWidth = 5.0,
                )
            }
        }
    }
}
