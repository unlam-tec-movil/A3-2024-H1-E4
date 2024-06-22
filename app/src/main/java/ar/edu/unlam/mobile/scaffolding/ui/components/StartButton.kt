package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.ui.theme.displayFontFamily

@Preview
@Composable
fun StartButton(
    modifier: Modifier = Modifier,
    action: () -> Unit = {},
) {
    val transitionDuration = 800
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(transitionDuration),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "",
    )

    val buttonColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.secondaryContainer,
        targetValue = MaterialTheme.colorScheme.onSecondaryContainer,
        animationSpec =
            infiniteRepeatable(
                animation = tween(transitionDuration),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "",
    )

    val textColor by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colorScheme.onSecondaryContainer,
        targetValue = MaterialTheme.colorScheme.secondaryContainer,
        animationSpec =
            infiniteRepeatable(
                animation = tween(transitionDuration),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "",
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .scale(scale)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .height(70.dp)
                .background(buttonColor),
    ) {
        Button(
            onClick = { action() },
            modifier =
                modifier
                    .fillMaxWidth()
                    .scale(scale),
            colors =
                ButtonColors(
                    containerColor = buttonColor,
                    contentColor = buttonColor,
                    disabledContentColor = buttonColor,
                    disabledContainerColor = buttonColor,
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play arrow",
                    tint = textColor,
                )
                Text(
                    text = "Iniciar",
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = displayFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                )
            }
        }
    }
}
