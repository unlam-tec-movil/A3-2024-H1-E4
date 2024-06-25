package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun ActivityResult(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth().padding(bottom = 10.dp)) {
        Image(
            painter = painterResource(id = R.drawable.map_result),
            contentDescription = "Trazado del recorrido en un mapa",
            modifier = modifier.padding(10.dp).size(220.dp),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier.padding(bottom = 10.dp),
        ) {
            CardResultActivity(titulo = "Dist. Total", valor = "1.5", unidad = "KM")
            Spacer(modifier = modifier.padding(end = 10.dp))
            CardResultActivity(titulo = "Tiempo Total", valor = "0:55")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            CardResultActivity(titulo = "Calorías", valor = "350", unidad = "Kcal")
            Spacer(modifier = modifier.padding(end = 10.dp))
            CardResultActivity(titulo = "Vel. Máx.", valor = "6.5", unidad = "KM/h")
            Spacer(modifier = modifier.padding(end = 10.dp))
            CardResultActivity(titulo = "Vel. Prom.", valor = "5.0", unidad = "KM/h")
        }
    }
}
