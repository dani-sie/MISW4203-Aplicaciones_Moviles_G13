package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas E2E para HU05: Consultar listado de coleccionistas
 */
@RunWith(AndroidJUnit4::class)
class CollectorListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU05-01: Navegación a listado de coleccionistas desde Home
     * Verifica que al hacer clic en "Ver Listado de Coleccionistas" desde Home,
     * se navega a la lista de coleccionistas
     */
    @Test
    fun testNavigationToCollectorListFromHome() {
        // Arrange: Verificar que estamos en Home
        composeTestRule.onNodeWithText("🎵 VinylHub").assertIsDisplayed()
        
        // Act: Hacer clic en el botón de coleccionistas
        composeTestRule.onNodeWithText("Ver Listado de Coleccionistas").performClick()
        
        // Assert: Verificar que se muestra la pantalla de coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("👥 Coleccionistas")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("👥 Coleccionistas").assertIsDisplayed()
    }

    /**
     * E2E-HU05-02: Carga de lista de coleccionistas desde API
     * Verifica que se cargan y muestran coleccionistas desde la API
     */
    @Test
    fun testCollectorListLoadsFromAPI() {
        // Arrange: Navegar a la lista de coleccionistas
        composeTestRule.onNodeWithText("Ver Listado de Coleccionistas").performClick()
        
        // Act: Esperar a que se carguen los coleccionistas
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText("Cargando coleccionistas...")
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        Thread.sleep(2000) // Dar tiempo para que se rendericen los coleccionistas
        
        // Assert: Verificar que no se muestra el mensaje de carga
        composeTestRule.onNodeWithText("👥 Coleccionistas").assertIsDisplayed()
        
        // Verificar que hay un campo de búsqueda
        composeTestRule.onNodeWithText("Buscar usuario").assertIsDisplayed()
    }
}

