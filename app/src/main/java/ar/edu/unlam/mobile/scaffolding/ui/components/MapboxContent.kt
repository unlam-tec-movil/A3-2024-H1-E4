package ar.edu.unlam.mobile.scaffolding.ui.components

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ar.edu.unlam.mobile.scaffolding.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mapbox.common.MapboxOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

@OptIn(MapboxExperimental::class, ExperimentalPermissionsApi::class)
@Composable
fun MapboxContent(
    modifier: Modifier = Modifier,
    mapViewportState: MapViewportState,
) {
    MapboxOptions.accessToken = stringResource(id = R.string.mapbox_access_token)
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(key1 = true) {
        permissionState.launchPermissionRequest()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier, // .height(660.dp).width(400.dp) medidas aproximadas para cumplir con el diseño de la UI
    ) {
        if (permissionState.status.isGranted) {
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
            }
        } else {
            NoLocationPermissionContent()
        }
    }
}
