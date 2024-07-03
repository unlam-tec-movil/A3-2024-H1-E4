package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class HistoryHeaderTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHistoryHeaderDisplayed() {
        composeTestRule.setContent {
            HistoryHeader(onBackClick = {})
        }

        // verifica que el icono muestre descripcion
        composeTestRule.onNodeWithContentDescription("Volver").assertExists()

        // verifica que el titulo sea correcto
        composeTestRule.onNodeWithText("Actividades").assertExists()
    }

    @Test
    fun testHistoryHeaderBackButtonClick() {
        var clicked = false
        composeTestRule.setContent {
            HistoryHeader(
                onBackClick = { clicked = true }
            )
        }

        // hace click en el boton volver
        composeTestRule.onNodeWithContentDescription("Volver").performClick()

        // verifica click registrado
        assert(clicked)
    }
}