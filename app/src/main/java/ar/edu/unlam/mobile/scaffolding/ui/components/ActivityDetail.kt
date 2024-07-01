package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route

@Preview
@Composable
fun ActivityDetail(
    route: Route = MockEntities.route,
    hide: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = { hide() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier =
                modifier
                    .size(width = 500.dp, height = 550.dp)
                    .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors =
                CardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
        ) {
            Text(
                text = "Detalle de la Actividad",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier =
                    modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
            )
            ActivityResult(route = route)
        }
    }
}
