package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
) {
    val locationUiState by locationViewModel.locationUiState.collectAsState()
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black),
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

                Text(
                    text = "Ver todos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(35, 79, 113, 255),
                    modifier = Modifier.clickable { navController.navigate(Routes.Awards.name) },
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                Log.i("PREV SCREEN LOCATION", "$locationUiState")
                Log.i("SCREEN LOCATION", "Coordinates= $locationUiState")

                CardAward("250 kcl", R.drawable.trueno, Modifier.weight(1f)) { navController.navigate(Routes.ActivityScreen.name) }
            }
        }
        Column {
            MapContainer()
        }
    }
}
