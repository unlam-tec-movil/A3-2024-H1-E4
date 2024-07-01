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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.HistoryHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.HistoryItem
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.AchievementViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HistoryViewModel

@Preview
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
    achievementViewModel: AchievementViewModel = hiltViewModel(),
) {
    val historyUiState by historyViewModel.historyUiState.collectAsState()
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
                .background(MaterialTheme.colorScheme.background),
    ) {
        HistoryHeader(returnToHome)
        if (!historyUiState.loading) {
            LazyColumn(modifier = modifier.padding(horizontal = 5.dp, vertical = 10.dp)) {
                items(historyUiState.routes) { route ->
                    if (route != null) {
                        Spacer(modifier = modifier.padding(7.dp))
                        HistoryItem(route, rotation)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Butt,
                )
            }
        }
    }
}
