package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils

val fontSize = 18.sp
val subtitleFontSize = 14.sp

@Preview
@Composable
fun HistoryItem(
    route: Route = MockEntities.route,
    rotation: Float = 0.0f,
    modifier: Modifier = Modifier.rotate(rotation),
) {
    val showDetail = remember { mutableStateOf(false) }

    if (showDetail.value) {
        ActivityDetail(route = route, hide = { showDetail.value = false })
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier =
            modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(30.dp),
                )
                .fillMaxWidth()
                .height(100.dp)
                .border(
                    border =
                        BorderStroke(
                            10.dp,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    shape = RoundedCornerShape(30.dp),
                )
                .padding(5.dp)
                .clickable(onClick = { showDetail.value = true }),
    ) {
        Text(
            text = DateTimeUtils.formatDate(route.date),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 24.sp,
        )
        Spacer(modifier = modifier.padding(10.dp))
        Column {
            Text(
                text = "%.2f".format(route.distance),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = fontSize,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Kilómetros",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = subtitleFontSize,
            )
        }
        Spacer(modifier = modifier.padding(10.dp))
        Column {
            Text(
                text = DateTimeUtils.formatTime(route.durationSeconds),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = fontSize,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Tiempo",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = subtitleFontSize,
            )
        }
        Spacer(modifier = modifier.padding(10.dp))
        Column {
            Text(
                text = route.calories.toString(),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = fontSize,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Calorías",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = subtitleFontSize,
            )
        }
    }
}
