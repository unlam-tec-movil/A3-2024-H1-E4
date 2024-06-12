package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CardAward
import ar.edu.unlam.mobile.scaffolding.ui.components.HomeHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.MapContainer
import ar.edu.unlam.mobile.scaffolding.ui.components.StartSwitch
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.ChronometerViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HelloMessageUIState
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.HomeViewModel
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.LocationViewModel
import com.mapbox.maps.MapboxExperimental

@OptIn(MapboxExperimental::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    // La información que obtenemos desde el view model la consumimos a través de un estado de
    // "tres vías": Loading, Success y Error. Esto nos permite mostrar un estado de carga,
    // un estado de éxito y un mensaje de error.
    val uiState by viewModel.uiState.collectAsState()

    when (val homeUiState = uiState.helloMessageState) {
        is HelloMessageUIState.Loading -> {
            // Loading
        }

        is HelloMessageUIState.Success -> {
            MainScreen(navController)
        }

        is HelloMessageUIState.Error -> {
            // Error
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController,
    locationViewModel: LocationViewModel = hiltViewModel(),
    chronometerViewModel: ChronometerViewModel = hiltViewModel(),
) {
    Column(
        Modifier
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
            ) {
                Text(
                    text = "Premios conseguidos",
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
                    "4/15km",
                    R.drawable.copa,
                    Modifier.weight(1f),
                )
                CardAward("22 dias", R.drawable.fuego, Modifier.weight(1f))
                CardAward("500/2500 kcl", R.drawable.trueno, Modifier.weight(1f))
            }
        }
        Column {
            MapContainer()
            StartSwitch()
        }
    }
}
