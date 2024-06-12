package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun Achievement(
    nombre: String = "Kilómetros",
    modifier: Modifier = Modifier.padding(vertical = 2.dp),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiaryContainer, shape = RoundedCornerShape(5.dp))
                .border(
                    border =
                        BorderStroke(
                            5.dp,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                        ),
                    shape = RoundedCornerShape(5.dp),
                )
                .padding(10.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.copa),
            contentDescription = "Imagen del logro",
        )
        Column(modifier = modifier.padding(start = 50.dp)) {
            Text(text = nombre)
            Text(text = "15.5 / 25")
        }
    }
}
