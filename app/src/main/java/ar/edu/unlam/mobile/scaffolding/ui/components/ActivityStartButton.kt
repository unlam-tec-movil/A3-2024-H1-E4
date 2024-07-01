package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun ActivityStartButton(
    running: Boolean = true,
    onActivityStop: () -> Unit = {},
    onActivityStart: () -> Unit = {},
    swapRunningState: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            if (running) {
                onActivityStop()
            } else {
                onActivityStart()
            }
            swapRunningState()
        },
        modifier = Modifier.size(90.dp),
        shape = CircleShape,
        colors =
            ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            ),
    ) {
        Icon(
            painter =
                if (running) {
                    painterResource(
                        id = R.drawable.baseline_pause_24,
                    )
                } else {
                    painterResource(id = R.drawable.baseline_play_arrow_24)
                },
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier =
                Modifier
                    .size(64.dp),
        )
    }
}
