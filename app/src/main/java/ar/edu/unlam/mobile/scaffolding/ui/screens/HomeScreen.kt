package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.ui.components.CardAward
import ar.edu.unlam.mobile.scaffolding.ui.components.HomeHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.MapContainer
import ar.edu.unlam.mobile.scaffolding.ui.components.StartButton
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HomeCoordinateUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeUIState by viewModel.uiState.collectAsState()
    var isTouch by remember { mutableStateOf(false) }
    when (val coordinateUIState = homeUIState.coordinateUIState) {
        is HomeCoordinateUIState.Loading -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier =
                    Modifier.fillMaxSize().clickable {
                        if (!isTouch) {
                            viewModel.startLocationService()
                            isTouch = true
                        } else {
                            viewModel.stopLocationService()
                            isTouch = false
                        }
                    },
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        is HomeCoordinateUIState.Success -> {
            HomeScreenContent(
                coordinates = coordinateUIState.coordinateList,
                navController = navController,
                modifier = modifier,
            )
        }

        is HomeCoordinateUIState.Error -> {
            // Error
        }
    }
}

@Composable
fun HomeScreenContent(
    coordinates: List<Coordinate>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeHeader()
        Column(
            modifier =
                Modifier
                    .padding(12.dp, 8.dp)
                    .fillMaxWidth(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(2.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Logros",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
                OutlinedButton(
                    onClick = { navController.navigate(Routes.Awards.name) },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = "Ver todos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                CardAward(
                    "4KM",
                    R.drawable.copa,
                    Modifier.weight(1f),
                )
                CardAward("22 dias", R.drawable.fuego, Modifier.weight(1f))
                CardAward("500 kcal", R.drawable.trueno, Modifier.weight(1f))
            }
        }
        Column {
            MapContainer(coordinates = coordinates)
            StartButton()
        }
    }
}
