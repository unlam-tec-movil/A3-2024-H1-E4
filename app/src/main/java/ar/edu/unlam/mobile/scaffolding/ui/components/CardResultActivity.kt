package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardResultActivity(
    title: String = "ejemplo",
    value: String = "123",
    modifier: Modifier,
) {
//    Box(
//        modifier
//            .padding(8.dp)
//            .background(
//                color = Color(35, 79, 113, 255),
//                shape = RoundedCornerShape(16.dp),
//            )
//            .wrapContentHeight(),
//    ) {
    Column(
        modifier =
        Modifier
            .background(
                color = Color(35, 79, 113, 255),
                shape = RoundedCornerShape(16.dp),
            )
            .padding(top = 5.dp, bottom = 5.dp)
            .width(110.dp)
            .wrapContentSize()
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = modifier.padding(start = 10.dp)
        )
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier =
            Modifier
                .fillMaxWidth(),
        )
    }
//    }
}
