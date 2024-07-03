package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun CardAward(
    title: String = "Kilómetros",
    amount: String = "123",
    img: Int = R.drawable.trueno,
    modifier: Modifier = Modifier,
    action: () -> Unit = {},
) {
    Surface(
        modifier =
            modifier
                .padding(0.dp, 0.dp)
                .width(160.dp)
                .height(80.dp),
        shape = RoundedCornerShape(5.dp),
        color = MaterialTheme.colorScheme.tertiary,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier =
                    Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = img),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .width(18.dp)
                            .height(18.dp),
                )
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
            Text(text = amount, fontSize = 16.sp)
        }
    }
}
