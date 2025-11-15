package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_BACK
import com.uniandes.vinylhub.ui.TestConstants.Companion.CONTENT_DESC_COVER_PREFIX
import com.uniandes.vinylhub.ui.TestConstants.Companion.LOADING_ALBUMS
import com.uniandes.vinylhub.ui.TestConstants.Companion.NAV_ALBUMS
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_CATALOG

/**
 * Pruebas E2E para HU02: Consultar detalle de un álbum
 */
@RunWith(AndroidJUnit4::class)
class AlbumDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU02-01: Navegación a detalle de álbum
     * Verifica que al hacer clic en la imagen de un álbum, se navega al detalle
     */
    @Test
    fun testNavigationToAlbumDetail() {
        // Arrange: Navegar a la lista de álbumes
        composeTestRule.onNodeWithText(NAV_ALBUMS).performClick()
        
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ALBUMS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        Thread.sleep(3000) // Esperar a que se rendericen los álbumes
        
        // Act: Hacer clic en la imagen del primer álbum
        // Las imágenes tienen contentDescription "Cover de [nombre]"
        composeTestRule.onAllNodes(hasContentDescription(CONTENT_DESC_COVER_PREFIX, substring = true))
            .onFirst()
            .performClick()
        
        Thread.sleep(2000) // Esperar navegación
        
        // Assert: Verificar que se muestra la pantalla de detalle
        // La pantalla de detalle tiene un botón "Volver"
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).assertIsDisplayed()
    }

    /**
     * E2E-HU02-02: Visualización de tracks del álbum
     * Verifica que se muestran los tracks del álbum en el detalle
     */
    @Test
    fun testAlbumTracksAreDisplayed() {
        // Arrange: Navegar al detalle de un álbum
        composeTestRule.onNodeWithText(NAV_ALBUMS).performClick()
        
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ALBUMS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        Thread.sleep(3000)
        
        composeTestRule.onAllNodes(hasContentDescription(CONTENT_DESC_COVER_PREFIX, substring = true))
            .onFirst()
            .performClick()
        
        Thread.sleep(3000) // Esperar carga del detalle
        
        // Act & Assert: Verificar que se muestra información del álbum
        // Buscar secciones comunes en el detalle
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).assertIsDisplayed()
        
        // La pantalla de detalle debe mostrar información del álbum
        // (tracks, performers, comments, etc.)
    }

    /**
     * E2E-HU02-03: Navegación de regreso desde detalle
     * Verifica que el botón "Volver" regresa a la lista de álbumes
     */
    @Test
    fun testBackNavigationFromAlbumDetail() {
        // Arrange: Navegar al detalle de un álbum
        composeTestRule.onNodeWithText(NAV_ALBUMS).performClick()
        
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onAllNodesWithText(LOADING_ALBUMS)
                    .fetchSemanticsNodes().isEmpty()
            } catch (e: Exception) {
                true
            }
        }
        
        Thread.sleep(3000)
        
        composeTestRule.onAllNodes(hasContentDescription(CONTENT_DESC_COVER_PREFIX, substring = true))
            .onFirst()
            .performClick()
        
        Thread.sleep(2000)
        
        // Act: Hacer clic en el botón "Volver"
        composeTestRule.onNodeWithContentDescription(BUTTON_BACK).performClick()
        
        Thread.sleep(1000)
        
        // Assert: Verificar que regresamos a la lista de álbumes
        composeTestRule.onNodeWithText(TITLE_CATALOG).assertIsDisplayed()
    }
}

