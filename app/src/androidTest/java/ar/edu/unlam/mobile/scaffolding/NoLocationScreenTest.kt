package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffolding.ui.components.NoLocationPermissionContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoLocationScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun whenDisplayNoLocationScreen() {
        rule.setContent {
            NoLocationPermissionContent()
        }

        rule.onNodeWithText(text = "La aplicación requiere permisos de ubicación precisa para funcionar correctamente.")
            .assertExists()
    }
}
