package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import ar.edu.unlam.mobile.scaffolding.ui.screens.RegisterScreen
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {
    @get:Rule
    val rule = createComposeRule()
    @Test
    fun whenRegisterScreenLoads_ShowsCorrectFields() {
        rule.setContent { RegisterScreen() }

        rule.onNodeWithText("Nombre").assertExists()
        rule.onNodeWithText("Apellido").assertExists()
        rule.onNodeWithText("Altura (cm)").assertExists()
        rule.onNodeWithText("Peso (Kg)").assertExists()
        rule.onNodeWithText("Edad").assertExists()
        rule.onNodeWithText("Continuar").assertExists()
    }
    @Test
    fun whenClickingContinueWithEmptyFields_ShowsErrorMessage() {
        rule.setContent { RegisterScreen() }

        rule.onNodeWithText("Continuar")
            .assertExists()
            .performClick()

        // Aca asumimos que Toast va a mostrar el mensaje "Todos los datos son obligatorios!"
        rule.onNodeWithText("Todos los datos son obligatorios!").assertIsDisplayed()
    }
    @Test
    fun whenCompleteAllFieldsAndClickingContinue_NavigatesToHome() {
        rule.setContent { RegisterScreen() }

        // Llenar los campos con datos validos
        rule.onNodeWithText("Nombre").performTextInput("Emanuel")
        rule.onNodeWithText("Apellido").performTextInput("Cisterna")
        rule.onNodeWithText("Altura (cm)").performTextInput("180")
        rule.onNodeWithText("Peso (Kg)").performTextInput("82")
        rule.onNodeWithText("Edad").performTextInput("24")

        rule.onNodeWithText("Continuar")
            .assertExists()
            .performClick()

        // Verificar que se navegue a la pantalla Home
        rule.onNodeWithText("Home").assertIsDisplayed()
    }
}