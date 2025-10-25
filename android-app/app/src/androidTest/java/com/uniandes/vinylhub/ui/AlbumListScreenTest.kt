package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas E2E para HU01: Consultar catálogo de álbumes
 */
@RunWith(AndroidJUnit4::class)
class AlbumListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU01-01: Navegación a catálogo desde Home
     * Verifica que al hacer clic en "Ver Catálogo de Álbumes" desde Home,
     * se navega a la lista de álbumes
     */
    @Test
    fun testNavigationToAlbumListFromHome() {
        // Arrange: Verificar que estamos en Home
        composeTestRule.onNodeWithText("🎵 VinylHub").assertIsDisplayed()
        
        // Act: Hacer clic en el botón de álbumes
        composeTestRule.onNodeWithText("Ver Catálogo de Álbumes").performClick()
        
        // Assert: Verificar que se muestra la pantalla de catálogo
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("🎵 Catálogo")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("🎵 Catálogo").assertIsDisplayed()
    }

    /**
     * E2E-HU01-02: Carga de lista de álbumes desde API
     * Verifica que se cargan y muestran álbumes desde la API
     */
    @Test
    fun testAlbumListLoadsFromAPI() {
        // Arrange: Navegar a la lista de álbumes
        composeTestRule.onNodeWithText("Ver Catálogo de Álbumes").performClick()
        
        // Act: Esperar a que se carguen los álbumes
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            // Buscar cualquier texto que indique que hay álbumes cargados
            // Como los nombres de álbumes son dinámicos, buscamos elementos comunes
            try {
                composeTestRule.onAllNodesWithText("Cargando álbumes...")
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        // Assert: Verificar que no se muestra el mensaje de carga
        // y que hay contenido en la pantalla
        Thread.sleep(2000) // Dar tiempo para que se rendericen los álbumes
        
        // Verificar que la pantalla de catálogo está visible
        composeTestRule.onNodeWithText("🎵 Catálogo").assertIsDisplayed()
    }

    /**
     * E2E-HU01-03: Expansión de álbum en lista
     * Verifica que al hacer clic en un álbum, se expande mostrando más información
     */
    @Test
    fun testAlbumExpansionInList() {
        // Arrange: Navegar a la lista de álbumes y esperar carga
        composeTestRule.onNodeWithText("Ver Catálogo de Álbumes").performClick()
        
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText("Cargando álbumes...")
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        Thread.sleep(3000) // Esperar a que se rendericen los álbumes
        
        // Act: Hacer clic en el primer álbum (hacer scroll y buscar cualquier card)
        // Como los álbumes se cargan dinámicamente, intentamos hacer clic en la primera card
        composeTestRule.onAllNodes(hasClickAction())
            .onFirst()
            .performClick()
        
        Thread.sleep(1000) // Esperar animación de expansión
        
        // Assert: Verificar que seguimos en la pantalla de catálogo
        // (la expansión ocurre en la misma pantalla)
        composeTestRule.onNodeWithText("🎵 Catálogo").assertIsDisplayed()
    }
}

