package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ar.edu.unlam.mobile.scaffolding.R
import com.mapbox.maps.extension.style.expressions.dsl.generated.color

@Preview
@Composable
fun header(){
    Column() {
        ConstraintLayout {
            val (topImg, profile) = createRefs()
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .background(
                        color = Color(35, 79, 113, 255),
                        //shape = RoundedCornerShape(bottomEnd = 35.dp, bottomStart = 35.dp)
                    )
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(

                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Bienvenido,",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(255, 255, 255)
                        )
                        Text(
                            text = "Emanuel Cisterna",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(255, 255, 255)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .clickable { }
                    )
                }
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    .shadow(3.dp, shape = RoundedCornerShape(20.dp))
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .constrainAs(profile) {
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ){
                Text(
                    text = "Tu actividad de hoy",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 12.dp, start = 12.dp)
                )
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val (box1, box2) = createRefs()
                    CardResultActivity("Distancia", "6,12",
                        Modifier.constrainAs(box1) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(box2.start)
                            width = Dimension.percent(0.5f)
                        }
                    )
                    CardResultActivity("Calorias", "548",
                        Modifier.constrainAs(box2) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(box2.start)
                            width = Dimension.percent(0.5f)
                        }
                    )
                }
            }
        }
    }
}