package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.Achievement
import ar.edu.unlam.mobile.scaffolding.ui.components.AchievementHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityResult
import ar.edu.unlam.mobile.scaffolding.ui.components.ShareButton

@Preview
@Composable
fun AwardsScreen(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val returnToHome = {
        navController.navigate(Routes.Home.name)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(state = scrollState),
    ) {
        AchievementHeader(returnToHome)
        Column(modifier = modifier.padding(horizontal = 30.dp, vertical = 30.dp)) {
            Achievement("Distancia (Kilómetros)", R.drawable.shoe_prints, 3.3, 10.0, 2, 2)
            Achievement("Calorías consumidas", R.drawable.flame, 5300.0, 7800.0, 0, 5)
            Achievement("Minutos en Actividad", R.drawable.clock_lines, 300.0, 800.0, 0, 6)
            Achievement("Días de Actividad", R.drawable.calendar_days, 15.0, 50.0, 0, 4)
        }
        Text(
            text = "Tu mejor actividad",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
        )
        ActivityResult()
        Spacer(modifier = modifier.padding(10.dp))
        ShareButton()
        Spacer(modifier = modifier.padding(10.dp))
    }
}
