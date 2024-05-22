package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun Header() {
    ConstraintLayout(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(10.dp, 20.dp),
    ) {
        val (box1, box2) = createRefs()

        Box(
            modifier =
                Modifier
                    .constrainAs(box1) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(box2.start)
                        width = Dimension.percent(0.7f)
                    }
                    .wrapContentHeight(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                Box(
                    modifier =
                        Modifier
                            .width(168.dp)
                            .height(168.dp)
                            .background(
                                color = Color(35, 79, 113, 255),
                                shape = RoundedCornerShape(100),
                            ),
                ) {
                    Text(
                        text = "145",
                        fontSize = 48.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .offset(y = 4.dp),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.copa),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .width(168.dp)
                                .height(136.dp)
                                .offset(x = 0.dp, y = 50.dp),
                    )
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .constrainAs(box2) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(box1.end)
                        end.linkTo(parent.end)
                        width = Dimension.percent(0.3f)
                    }
                    .wrapContentHeight(),
            horizontalAlignment = Alignment.End,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fuego),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .width(32.dp)
                            .height(32.dp),
                )
                Text(
                    text = "145",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier,
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trueno),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .width(30.dp)
                            .height(30.dp),
                )
                Text(
                    text = "16",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier,
                )
            }
        }
    }
}
