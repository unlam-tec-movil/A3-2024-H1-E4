package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.models.Coordinate
import com.mapbox.maps.MapboxExperimental

@OptIn(MapboxExperimental::class)
@Composable
fun MapContainer(coordinates: List<Coordinate>) {
    Box(
        modifier =
            Modifier
                .padding(14.dp, 18.dp)
                .border(BorderStroke(2.dp, Color.White))
                .fillMaxWidth()
                .height(500.dp),
    ) {
        MapboxContent(
            modifier = Modifier.fillMaxSize(),
            locationCoordinates = coordinates,
        )
    }
}
