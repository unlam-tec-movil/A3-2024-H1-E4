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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.user.User

@Preview
@Composable
fun HomeHeader(user: User = User(1, "Juan", "Pérez", 22, 171, 75.0, 150.0, 5000, 300, 10)) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                ),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(R.string.welcome_message),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = "${user.firstName}!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .clickable { },
                )
            }
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.87f)
                    .padding(top = 10.dp, start = 12.dp, end = 12.dp)
                    .shadow(3.dp, shape = RoundedCornerShape(5.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Text(
                text = "Tu última actividad",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(top = 12.dp, start = 12.dp),
            )
            ActivityResult()
        }
    }
}
