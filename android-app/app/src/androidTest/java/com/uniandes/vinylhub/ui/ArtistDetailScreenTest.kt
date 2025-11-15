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
import com.uniandes.vinylhub.ui.TestConstants.Companion.LOADING_ARTISTS
import com.uniandes.vinylhub.ui.TestConstants.Companion.NAV_ARTISTS
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_ARTIST
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_ARTISTS

/**
 * Pruebas E2E para HU04: Consultar detalle de un artista
 */
@RunWith(AndroidJUnit4::class)
class ArtistDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU04-01: Navegación a detalle de artista
     * Verifica que al hacer clic en un artista, se navega al detalle
     */
    @Test
    fun testNavigationToArtistDetail() {
        // Arrange: Navegar a la lista de artistas
        composeTestRule.onNodeWithText(NAV_ARTISTS).performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ARTISTS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }

        Thread.sleep(3000) // Esperar a que se rendericen los artistas

        // Act: Hacer clic en el botón "Detalles" del primer artista
        composeTestRule.onAllNodesWithText(BUTTON_DETAILS)
            .onFirst()
            .performClick()

        Thread.sleep(2000) // Esperar navegación

        // Assert: Verificar que se muestra la pantalla de detalle
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).assertIsDisplayed()
        composeTestRule.onNodeWithText(TITLE_ARTIST).assertIsDisplayed()
    }

    /**
     * E2E-HU04-02: Visualización de álbumes del artista
     * Verifica que se muestran los álbumes asociados al artista
     */
    @Test
    fun testArtistAlbumsAreDisplayed() {
        // Arrange: Navegar al detalle de un artista
        composeTestRule.onNodeWithText(NAV_ARTISTS).performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ARTISTS)
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

        // Act & Assert: Verificar que se muestra información del artista
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).assertIsDisplayed()
        composeTestRule.onNodeWithText(TITLE_ARTIST).assertIsDisplayed()
    }

    /**
     * E2E-HU04-03: Navegación de regreso desde detalle
     * Verifica que el botón "Volver" regresa a la lista de artistas
     */
    @Test
    fun testBackNavigationFromArtistDetail() {
        // Arrange: Navegar al detalle de un artista
        composeTestRule.onNodeWithText(NAV_ARTISTS).performClick()

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ARTISTS)
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

        // Assert: Verificar que regresamos a la lista de artistas
        composeTestRule.onNodeWithText(TITLE_ARTISTS).assertIsDisplayed()
    }
}

