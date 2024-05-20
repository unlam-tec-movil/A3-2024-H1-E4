package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import androidx.compose.ui.res.vectorResource
import androidx.constraintlayout.compose.Dimension
import ar.edu.unlam.mobile.scaffolding.ui.components.Header
import ar.edu.unlam.mobile.scaffolding.ui.components.ShareButton
import com.mapbox.maps.extension.style.expressions.dsl.generated.color
import kotlin.math.round

@Preview
@Composable
fun AwardsScreen(){
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black)
            .padding(6.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
        ShareButton()
        Text(
            text = "Tu mejor actividad",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp )
        )
    }
}