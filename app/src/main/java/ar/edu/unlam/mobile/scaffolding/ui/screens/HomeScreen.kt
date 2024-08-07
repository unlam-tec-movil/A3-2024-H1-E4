package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.AchievementRow
import ar.edu.unlam.mobile.scaffolding.ui.components.ActivityResult
import ar.edu.unlam.mobile.scaffolding.ui.components.HomeHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.NoActivity
import ar.edu.unlam.mobile.scaffolding.ui.components.StartButton
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HomeViewModel

@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val userUiState by viewModel.userUiState.collectAsState()
    val routeUiState by viewModel.routeUiState.collectAsState()

    if (userUiState.loading) {
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
    } else {
        Column(
            modifier =
                modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HomeHeader(user = userUiState.user)
            if (routeUiState.error.isNotBlank()) {
                Spacer(modifier = modifier.padding(10.dp))
                NoActivity()
            } else if (!routeUiState.loading) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.55f)
                            .padding(top = 10.dp, start = 12.dp, end = 12.dp)
                            .shadow(3.dp, shape = RoundedCornerShape(5.dp))
                            .background(color = MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Tu última actividad",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                        )
                        Spacer(modifier = modifier.weight(1f))
                        OutlinedButton(
                            onClick = { navController.navigate(Routes.RouteHistory.name) },
                            shape = RoundedCornerShape(10.dp),
                            border =
                                BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                ),
                            colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colorScheme.primary),
                            modifier = modifier.padding(end = 10.dp),
                        ) {
                            Text(
                                text = "Ver todas",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                        }
                    }
                    ActivityResult(route = routeUiState.route!!)
                }
            } else {
                Column(
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
            Column(
                modifier =
                    Modifier
                        .padding(12.dp, 8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.25f)
                            .padding(2.dp, 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Logros",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.height(25.dp),
                    )
                    OutlinedButton(
                        onClick = { navController.navigate(Routes.Awards.name) },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
                        colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colorScheme.primary),
                    ) {
                        Text(
                            text = "Más Detalles",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
                AchievementRow(userUiState.user)
                Spacer(modifier = modifier.padding(10.dp))
                StartButton(action = {
                    navController.navigate("${Routes.ActivityProgressScreen.name}/${userUiState.user.weight}/${userUiState.user.id}")
                }, modifier = modifier.height(80.dp))
            }
        }
    }
}
