package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.AchievementMultipliers
import ar.edu.unlam.mobile.scaffolding.ui.components.Achievement
import ar.edu.unlam.mobile.scaffolding.ui.components.AchievementHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityResult
import ar.edu.unlam.mobile.scaffolding.ui.components.ShareButton
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.AchievementViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HomeViewModel

@Preview
@Composable
fun AwardsScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    achievementViewModel: AchievementViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val userUiState by homeViewModel.userUiState.collectAsState()
    val routeUiState by achievementViewModel.routeUiState.collectAsState()
    val scrollState = rememberScrollState()
    val returnToHome = {
        navController.navigate(Routes.Home.name)
    }
    val sensorValue by achievementViewModel.sensorValue.collectAsState()
    val rotation by animateFloatAsState(
        if (sensorValue > 5) {
            10f
        } else if (sensorValue < -5) {
            -10f
        } else {
            0f
        },
        label = "",
    )

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
        if (!userUiState.loading) {
            val user = userUiState.user
            Column(modifier = modifier.padding(horizontal = 30.dp, vertical = 30.dp)) {
                Achievement(
                    "Distancia (Kilómetros)",
                    R.drawable.shoe_prints,
                    userUiState.user.kilometers,
                    AchievementMultipliers.getKilometerLevel(user.kilometers).second,
                    2,
                    AchievementMultipliers.getKilometerLevel(user.kilometers).first,
                    rotation,
                )
                Achievement(
                    "Calorías consumidas",
                    R.drawable.flame,
                    userUiState.user.calories.toDouble(),
                    AchievementMultipliers.getCalorieLevel(user.calories).second,
                    0,
                    AchievementMultipliers.getCalorieLevel(user.calories).first,
                    rotation,
                )
                Achievement(
                    "Minutos en Actividad",
                    R.drawable.clock_lines,
                    userUiState.user.minutes.toDouble(),
                    AchievementMultipliers.getMinuteLevel(user.minutes).second,
                    0,
                    AchievementMultipliers.getMinuteLevel(user.minutes).first,
                    rotation,
                )
                Achievement(
                    "Días de Actividad",
                    R.drawable.calendar_days,
                    userUiState.user.days.toDouble(),
                    AchievementMultipliers.getDayLevel(user.days).second,
                    0,
                    AchievementMultipliers.getDayLevel(user.days).first,
                    rotation,
                )
            }
            if (routeUiState.error.isBlank()) {
                if (!routeUiState.loading) {
                    Text(
                        text = "Tu mejor actividad",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier =
                            modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                    )
                    ActivityResult(route = routeUiState.route!!)
                    Spacer(modifier = modifier.padding(10.dp))
                    ShareButton()
                    Spacer(modifier = modifier.padding(10.dp))
                } else {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.size(32.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeCap = StrokeCap.Butt,
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Butt,
                )
            }
        }
    }
}
