package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun CardAward(
    title: String = "125",
    img: Int = R.drawable.trueno,
    modifier: Modifier,
) {
    Button(
        onClick = {},
        modifier = modifier.padding(1.dp, 0.dp),
        shape = RoundedCornerShape(32.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(35, 79, 113, 255),
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
            // verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = null,
                modifier =
                    Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .clickable { },
            )
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(start = 4.dp),
            )
        }
    }
}
