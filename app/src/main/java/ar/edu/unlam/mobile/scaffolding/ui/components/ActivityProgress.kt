package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun ActivityProgress(prevFun: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp, 12.dp)
                .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.padding(12.dp))
        Button(
            onClick = {
                prevFun()
            },
            modifier = Modifier.size(90.dp),
            shape = CircleShape,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color(35, 79, 113, 255),
                ),
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(64.dp),
            )
        }
        Text(
            text = "48'",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 64.sp,
        )
        Text(
            text = "Tiempo",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )
    }
}
