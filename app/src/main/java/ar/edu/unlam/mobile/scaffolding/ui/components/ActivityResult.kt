package ar.edu.unlam.mobile.scaffolding.ui.components

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils
import ar.edu.unlam.mobile.scaffolding.utils.RouteImageUtils
import coil.compose.AsyncImage

@Suppress("ktlint:standard:max-line-length")
@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun ActivityResult(
    modifier: Modifier = Modifier,
    route: Route = MockEntities.route,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
    ) {
        BoxWithConstraints {
            if (maxHeight > 400.dp) {
                AsyncImage(
                    model =
                        RouteImageUtils.getTourDoneUrl(
                            routeString = route.coordinates,
                        ),
                    contentDescription = "recorrido hecho",
                    modifier =
                        Modifier
                            .padding(7.dp)
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
                valor = "%.2f".format(route.distance),
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
                valor = "%.2f".format(route.calories.toDouble()),
                unidad = "Kcal",
                modifier = modifier.weight(1f),
            )
            Spacer(modifier = modifier.padding(end = 10.dp))
            ActivityResultCard(
                titulo = "Vel. Máx.",
                valor = "%.2f".format(route.maxSpeed),
                unidad = "KM/h",
                modifier = modifier.weight(1f),
            )
            Spacer(modifier = modifier.padding(end = 10.dp))
            ActivityResultCard(
                titulo = "Vel. Prom.",
                valor = "%.2f".format(route.avgSpeed),
                unidad = "KM/h",
                modifier = modifier.weight(1f),
            )
        }
    }
}
