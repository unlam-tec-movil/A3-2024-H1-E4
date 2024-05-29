package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun StartSwitch(modifier: Modifier = Modifier) {
    var switch by remember {
        mutableStateOf(
            ToggleableInfo(
                isChecked = false,
                text = "Empezar"
            )
        )
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Switch(
            checked = switch.isChecked,
            onCheckedChange = { isChecked -> switch = switch.copy(isChecked) },
            modifier = modifier
                .scale(scaleX = 5f, scaleY = 2f)
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}