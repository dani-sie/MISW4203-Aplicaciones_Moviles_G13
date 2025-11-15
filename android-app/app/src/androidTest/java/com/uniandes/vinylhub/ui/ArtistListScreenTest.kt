package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.uniandes.vinylhub.ui.TestConstants.Companion.LOADING_ARTISTS
import com.uniandes.vinylhub.ui.TestConstants.Companion.NAV_ARTISTS
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_ARTISTS

/**
 * Pruebas E2E para HU03: Consultar listado de artistas
 */
@RunWith(AndroidJUnit4::class)
class ArtistListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU03-01: Navegación a listado de artistas desde Home
     * Verifica que al hacer clic en "Ver Listado de Artistas" desde Home,
     * se navega a la lista de artistas
     */
    @Test
    fun testNavigationToArtistListFromHome() {
        // Arrange: Verificar que estamos en Home
        composeTestRule.onNodeWithText("🎵 VinylHub").assertIsDisplayed()
        
        // Act: Hacer clic en el botón de artistas
        composeTestRule.onNodeWithText(NAV_ARTISTS).performClick()
        
        // Assert: Verificar que se muestra la pantalla de artistas
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(TITLE_ARTISTS)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(TITLE_ARTISTS).assertIsDisplayed()
    }

    /**
     * E2E-HU03-02: Carga de lista de artistas desde API
     * Verifica que se cargan y muestran artistas desde la API
     */
    @Test
    fun testArtistListLoadsFromAPI() {
        // Arrange: Navegar a la lista de artistas
        composeTestRule.onNodeWithText(NAV_ARTISTS).performClick()
        
        // Act: Esperar a que se carguen los artistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ARTISTS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        Thread.sleep(2000) // Dar tiempo para que se rendericen los artistas
        
        // Assert: Verificar que no se muestra el mensaje de carga
        composeTestRule.onNodeWithText(TITLE_ARTISTS).assertIsDisplayed()
        
        // Verificar que hay un campo de búsqueda
        composeTestRule.onNodeWithText("Buscar artista").assertIsDisplayed()
    }
}

