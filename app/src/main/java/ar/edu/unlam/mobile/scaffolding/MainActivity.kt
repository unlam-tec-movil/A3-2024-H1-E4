package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.ActivityScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.AwardsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.Routes
import ar.edu.unlam.mobile.scaffolding.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                    // ActivityScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val controller = rememberNavController()
    Scaffold(
        /*bottomBar = { BottomBar(controller = controller) },
        floatingActionButton = {
            IconButton(onClick = { controller.navigate("home") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
        },*/
    ) { paddingValue ->
        NavHost(navController = controller, startDestination = Routes.Home.name) {
            composable(Routes.Home.name) {
                MainScreen()
            }
            composable(Routes.Awards.name) {
                AwardsScreen(navController = controller)
            }
            composable(Routes.ActivityScreen.name) {
                ActivityScreen()
            }
        }
    }
}
