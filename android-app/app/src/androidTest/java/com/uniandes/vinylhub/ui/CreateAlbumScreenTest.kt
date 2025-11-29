package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.uniandes.vinylhub.ui.TestConstants.Companion.NAV_CREATE_ALBUM
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_CREATE_ALBUM
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_CREATE_ALBUM
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_BACK
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_ALBUM_TITLE
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_RELEASE_DATE
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_COVER_URL
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_RECORD_LABEL
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_GENRE
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_DESCRIPTION
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_ALBUM

/**
 * Pruebas E2E para HU07: Crear un álbum
 */
@RunWith(AndroidJUnit4::class)
class CreateAlbumScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU07-01: Navegación a formulario de creación desde Home
     * Verifica que al hacer clic en "Crear Álbum" desde Home,
     * se navega al formulario de creación de álbum
     */
    @Test
    fun testNavigationToCreateAlbumFromHome() {
        // Arrange: Verificar que estamos en Home
        composeTestRule.onNodeWithText("🎵 VinylHub").assertIsDisplayed()
        
        // Act: Hacer clic en el botón de crear álbum
        composeTestRule.onNodeWithText(NAV_CREATE_ALBUM).performClick()
        
        // Assert: Verificar que se muestra la pantalla de crear álbum
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(TITLE_CREATE_ALBUM)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(TITLE_CREATE_ALBUM).assertIsDisplayed()
    }

    /**
     * E2E-HU07-02: Visualización de campos del formulario
     * Verifica que se muestran todos los campos necesarios para crear un álbum
     */
    @Test
    fun testCreateAlbumFormFieldsAreDisplayed() {
        // Arrange: Navegar al formulario de creación
        composeTestRule.onNodeWithText(NAV_CREATE_ALBUM).performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(TITLE_CREATE_ALBUM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        Thread.sleep(1000) // Esperar a que se renderice el formulario

        // Assert: Verificar que los campos principales están presentes
        // Verificar campos visibles sin scroll
        composeTestRule.onNodeWithText(LABEL_ALBUM_TITLE).assertExists()
        composeTestRule.onNodeWithText(LABEL_RELEASE_DATE).assertExists()

        // Los demás campos pueden requerir scroll, solo verificamos que existen
        composeTestRule.onNodeWithText(LABEL_RECORD_LABEL).assertExists()
        composeTestRule.onNodeWithText(LABEL_GENRE).assertExists()
        composeTestRule.onNodeWithText(LABEL_DESCRIPTION).assertExists()

        // Verificar que el botón de crear está presente
        composeTestRule.onNodeWithText(BUTTON_CREATE_ALBUM).assertExists()

        // Verificar que el botón de volver está presente
        composeTestRule.onAllNodesWithText(BUTTON_BACK).onFirst().assertExists()
    }

    /**
     * E2E-HU07-03: Validación de campos requeridos
     * Verifica que el botón de crear está deshabilitado cuando faltan campos
     */
    @Test
    fun testCreateButtonDisabledWhenFieldsEmpty() {
        // Arrange: Navegar al formulario de creación
        composeTestRule.onNodeWithText(NAV_CREATE_ALBUM).performClick()
        
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(TITLE_CREATE_ALBUM)
                .fetchSemanticsNodes().isNotEmpty()
        }
        
        Thread.sleep(1000)
        
        // Assert: Verificar que el botón de crear está deshabilitado
        composeTestRule.onNodeWithText(BUTTON_CREATE_ALBUM).assertIsNotEnabled()
    }

    /**
     * E2E-HU07-04: Llenado completo del formulario y habilitación del botón
     * Verifica que al llenar todos los campos, el botón de crear se habilita
     */
    @Test
    fun testFormCompletionEnablesCreateButton() {
        // Arrange: Navegar al formulario de creación
        composeTestRule.onNodeWithText(NAV_CREATE_ALBUM).performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(TITLE_CREATE_ALBUM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        Thread.sleep(1000)

        // Verificar que el botón está deshabilitado inicialmente
        composeTestRule.onNodeWithText(BUTTON_CREATE_ALBUM).assertIsNotEnabled()

        // Act: Llenar todos los campos del formulario
        val testAlbumName = "Test E2E ${System.currentTimeMillis()}"

        // Encontrar y llenar el campo de título (primer campo de texto)
        composeTestRule.onAllNodes(hasSetTextAction())
            .onFirst()
            .performTextInput(testAlbumName)

        Thread.sleep(500)

        // Llenar fecha de lanzamiento (segundo campo de texto)
        composeTestRule.onAllNodes(hasSetTextAction())
            .filter(hasText(""))
            .onFirst()
            .performTextInput("01/01/2024")

        Thread.sleep(500)

        // Llenar URL de portada (tercer campo de texto)
        composeTestRule.onAllNodes(hasSetTextAction())
            .filter(hasText(""))
            .onFirst()
            .performTextInput("https://test.com/cover.jpg")

        Thread.sleep(500)

        // Seleccionar sello discográfico (hacer scroll al elemento primero)
        composeTestRule.onNode(hasText(LABEL_RECORD_LABEL) and hasClickAction())
            .performScrollTo()
            .performClick()
        Thread.sleep(500) // Esperar a que se abra el dropdown
        composeTestRule.onNodeWithText("EMI").performClick()

        Thread.sleep(500)

        // Seleccionar género
        composeTestRule.onNode(hasText(LABEL_GENRE) and hasClickAction())
            .performScrollTo()
            .performClick()
        Thread.sleep(500) // Esperar a que se abra el dropdown
        composeTestRule.onNodeWithText("Rock").performClick()

        Thread.sleep(500)

        // Llenar descripción (hacer scroll y llenar)
        composeTestRule.onAllNodes(hasSetTextAction())
            .filter(hasText(""))
            .onFirst()
            .performScrollTo()
            .performTextInput("Test description")

        Thread.sleep(1000) // Esperar a que se procesen los cambios

        // Assert: Verificar que el botón de crear está habilitado
        composeTestRule.onNodeWithText(BUTTON_CREATE_ALBUM)
            .performScrollTo()
            .assertIsEnabled()
    }

    /**
     * E2E-HU07-05: Navegación de regreso desde formulario
     * Verifica que el botón "Volver" regresa al menú principal
     */
    @Test
    fun testBackNavigationFromCreateAlbum() {
        // Arrange: Navegar al formulario de creación
        composeTestRule.onNodeWithText(NAV_CREATE_ALBUM).performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(TITLE_CREATE_ALBUM)
                .fetchSemanticsNodes().isNotEmpty()
        }

        Thread.sleep(1000)

        // Act: Hacer clic en el botón "Volver"
        composeTestRule.onAllNodesWithText(BUTTON_BACK)
            .onLast() // El último botón "Volver" es el del final del formulario
            .performScrollTo()
            .performClick()

        // Assert: Verificar que volvemos al Home
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("🎵 VinylHub")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("🎵 VinylHub").assertIsDisplayed()
    }
}


