package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HistoryViewModel
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils

@Preview
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val historyUiState by historyViewModel.historyUiState.collectAsState()
    val returnToHome = {
        navController.navigate(Routes.Home.name)
    }

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
            LazyColumn(modifier = modifier.padding(horizontal = 30.dp, vertical = 30.dp)) {
                items(historyUiState.routes) { route ->
                    Row {
                        Text(text = DateTimeUtils.formatTime(route?.date?.time!!))
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
