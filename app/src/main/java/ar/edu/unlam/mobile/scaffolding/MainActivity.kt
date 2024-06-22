package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.ActivityProgressScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.AwardsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.Routes
import ar.edu.unlam.mobile.scaffolding.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val controller = rememberNavController()
    Scaffold { paddingValue ->
        NavHost(navController = controller, startDestination = Routes.Home.name) {
            composable(Routes.Home.name) {
                HomeScreen(
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable(Routes.Awards.name) {
                AwardsScreen(navController = controller)
            }
            composable(Routes.ActivityProgressScreen.name) {
                ActivityProgressScreen(navController = controller)
            }
        }
    }
}
