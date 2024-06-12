package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.Achievement
import ar.edu.unlam.mobile.scaffolding.ui.components.AchievementHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.ShareButton

@Preview
@Composable
fun AwardsScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val returnToHome = {
        navController.popBackStack()
    }

    Column(
        modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AchievementHeader(returnToHome)
        Column(modifier = modifier.padding(horizontal = 30.dp, vertical = 30.dp)) {
            Achievement("Kilómetros")
            Achievement("Calorías consumidas")
            Achievement("Tiempo en Actividad")
        }
        Text(
            text = "Tu mejor actividad",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )
        ShareButton()
    }
}
