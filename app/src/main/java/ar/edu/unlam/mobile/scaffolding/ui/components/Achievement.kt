package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun Achievement(
    name: String = "Kilómetros",
    image: Int = R.drawable.copa,
    current: Double = 5.0,
    total: Double = 10.0,
    decimals: Int = 2,
    level: Int = 1,
    rotation: Float = 0f,
    modifier: Modifier =
        Modifier
            .padding(vertical = 2.dp)
            .rotate(rotation),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(5.dp),
                )
                .border(
                    border =
                        BorderStroke(
                            5.dp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                        ),
                    shape = RoundedCornerShape(5.dp),
                )
                .padding(10.dp),
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Imagen del logro",
            colorFilter = ColorFilter.tint(Color.Black),
            modifier =
                modifier
                    .fillMaxWidth(.15f)
                    .fillMaxHeight(.5f),
        )
        Column(modifier = modifier.padding(start = 30.dp).fillMaxWidth(.7f)) {
            Text(text = name)
            Text(text = "${"%.${decimals}f".format(current)} / ${"%.${decimals}f".format(total)}")
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(60.dp),
        ) {
            Text(text = "Nivel $level")
        }
    }
}
