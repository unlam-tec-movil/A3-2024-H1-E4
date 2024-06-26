package ar.edu.unlam.mobile.scaffolding.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun ActivityResult(
    route: Route = MockEntities.route,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val showImage = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
    ) {
        BoxWithConstraints {
            if (maxHeight > 400.dp) {
                Image(
                    painter = painterResource(id = R.drawable.map_result),
                    contentDescription = "Trazado del recorrido en un mapa",
                    modifier =
                        modifier
                            .padding(10.dp)
                            .size(240.dp),
                )
            } else {
                Spacer(modifier = modifier.padding(20.dp))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActivityResultCard(
                titulo = "Dist. Total",
                valor = "${route.distance}",
                unidad = "KM",
                modifier = modifier.weight(1f),
            )
            Spacer(modifier = modifier.padding(end = 10.dp))
            ActivityResultCard(
                titulo = "Tiempo Total",
                valor = DateTimeUtils.formatTime(route.durationSeconds),
                modifier = modifier.weight(1f),
            )
        }
        Spacer(modifier = modifier.padding(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActivityResultCard(
                titulo = "Calorías",
                valor = "${route.calories}",
                unidad = "Kcal",
                modifier = modifier.weight(1f),
            )
            Spacer(modifier = modifier.padding(end = 10.dp))
            ActivityResultCard(
                titulo = "Vel. Máx.",
                valor = "${route.maxSpeed}",
                unidad = "KM/h",
                modifier = modifier.weight(1f),
            )
            Spacer(modifier = modifier.padding(end = 10.dp))
            ActivityResultCard(
                titulo = "Vel. Prom.",
                valor = "${route.avgSpeed}",
                unidad = "KM/h",
                modifier = modifier.weight(1f),
            )
        }
    }
}
