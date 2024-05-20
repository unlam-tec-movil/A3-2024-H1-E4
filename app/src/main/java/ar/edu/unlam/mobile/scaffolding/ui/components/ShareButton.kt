package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun ShareButton(){
    Button (
        onClick = {},

        modifier = Modifier
            .padding(4.dp, 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(35, 79, 113, 255)
        )
    ){
        val shareIcon: Painter = painterResource(id = R.drawable.baseline_share_24)
        Icon(
            painter = shareIcon,
            contentDescription = "Favorite Icon",
            tint = Color.White,
            modifier = Modifier
                .padding(4.dp, 4.dp)
        )
        Text(
            text = "Compartir",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(end = 4.dp, top = 4.dp, bottom = 4.dp)

        )
    }
}