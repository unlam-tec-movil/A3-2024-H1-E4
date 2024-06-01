package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.Header
import ar.edu.unlam.mobile.scaffolding.ui.components.ShareButton

@Composable
fun AwardsScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black)
            .padding(6.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header()
        ShareButton()
        Text(
            text = "Tu mejor actividad",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
        )
    }
}
