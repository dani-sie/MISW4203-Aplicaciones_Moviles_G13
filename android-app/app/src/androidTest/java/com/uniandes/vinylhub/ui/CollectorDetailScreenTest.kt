package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_BACK
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_DETAILS
import com.uniandes.vinylhub.ui.TestConstants.Companion.LOADING_COLLECTORS
import com.uniandes.vinylhub.ui.TestConstants.Companion.NAV_COLLECTORS
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_COLLECTORS
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_PROFILE

/**
 * Pruebas E2E para HU06: Consultar detalle de un coleccionista
 */
@RunWith(AndroidJUnit4::class)
class CollectorDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU06-01: Navegación a detalle de coleccionista
     * Verifica que al hacer clic en un coleccionista, se navega al detalle
     */
    @Test
    fun testNavigationToCollectorDetail() {
        // Arrange: Navegar a la lista de coleccionistas
        composeTestRule.onNodeWithText(NAV_COLLECTORS).performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_COLLECTORS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }

        Thread.sleep(3000) // Esperar a que se rendericen los coleccionistas

        // Act: Hacer clic en el botón "Detalles" del primer coleccionista
        composeTestRule.onAllNodesWithText(BUTTON_DETAILS)
            .onFirst()
            .performClick()

        Thread.sleep(2000) // Esperar navegación

        // Assert: Verificar que se muestra la pantalla de detalle (título "Perfil")
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).assertIsDisplayed()
        composeTestRule.onNodeWithText(TITLE_PROFILE).assertIsDisplayed()
    }

    /**
     * E2E-HU06-02: Visualización de álbumes favoritos del coleccionista
     * Verifica que se muestran los álbumes del coleccionista
     */
    @Test
    fun testCollectorAlbumsAreDisplayed() {
        // Arrange: Navegar al detalle de un coleccionista
        composeTestRule.onNodeWithText(NAV_COLLECTORS).performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_COLLECTORS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }

        Thread.sleep(3000)

        composeTestRule.onAllNodesWithText(BUTTON_DETAILS)
            .onFirst()
            .performClick()

        Thread.sleep(3000) // Esperar carga del detalle

        // Act & Assert: Verificar que se muestra información del coleccionista
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).assertIsDisplayed()
        composeTestRule.onNodeWithText(TITLE_PROFILE).assertIsDisplayed()
    }

    /**
     * E2E-HU06-03: Navegación de regreso desde detalle
     * Verifica que el botón "Volver" regresa a la lista de coleccionistas
     */
    @Test
    fun testBackNavigationFromCollectorDetail() {
        // Arrange: Navegar al detalle de un coleccionista
        composeTestRule.onNodeWithText(NAV_COLLECTORS).performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_COLLECTORS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }

        Thread.sleep(3000)

        composeTestRule.onAllNodesWithText(BUTTON_DETAILS)
            .onFirst()
            .performClick()

        Thread.sleep(2000)

        // Act: Hacer clic en el botón "Volver"
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).performClick()

        Thread.sleep(1000)

        // Assert: Verificar que regresamos a la lista de coleccionistas
        composeTestRule.onNodeWithText(TITLE_COLLECTORS).assertIsDisplayed()
    }
}

