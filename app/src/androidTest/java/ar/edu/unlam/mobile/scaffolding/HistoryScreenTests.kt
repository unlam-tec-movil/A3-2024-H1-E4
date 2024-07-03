package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.ui.components.HistoryItem
import org.junit.Rule
import org.junit.Test

class HistoryScreenTests {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun whenLoadingItem_ShowsCorrectDetails() {
        rule.setContent { HistoryItem(route = MockEntities.route) }

        rule.onNodeWithText("Kilómetros").assertExists()
        rule.onNodeWithText("20.00").assertExists()
        rule.onNodeWithText("Tiempo").assertExists()
        rule.onNodeWithText("00:00:06").assertExists()
        rule.onNodeWithText("Calorías").assertExists()
        rule.onNodeWithText("300").assertExists()
    }

    @Test
    fun whenClickingItem_ShowsDetail() {
        rule.setContent { HistoryItem(route = MockEntities.route) }

        rule.onNode(
            hasText("01/07")
                and
                hasClickAction(),
        ).performClick()

        rule.onNodeWithText("Detalle de la Actividad").assertIsDisplayed()
    }
}
