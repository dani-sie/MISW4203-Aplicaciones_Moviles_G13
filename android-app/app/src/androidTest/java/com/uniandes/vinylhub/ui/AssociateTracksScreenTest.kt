package com.uniandes.vinylhub.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uniandes.vinylhub.presentation.MainActivity
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_BACK
import com.uniandes.vinylhub.ui.TestConstants.Companion.BUTTON_SAVE_ASSOCIATION
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_SELECT_ALBUM
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_TRACK_DURATION
import com.uniandes.vinylhub.ui.TestConstants.Companion.LABEL_TRACK_TITLE
import com.uniandes.vinylhub.ui.TestConstants.Companion.NAV_ASSOCIATE_TRACKS
import com.uniandes.vinylhub.ui.TestConstants.Companion.SECTION_ADD_NEW_TRACK
import com.uniandes.vinylhub.ui.TestConstants.Companion.SECTION_EXISTING_TRACKS
import com.uniandes.vinylhub.ui.TestConstants.Companion.TITLE_ASSOCIATE_TRACKS
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests E2E para HU08: Asociar tracks con un álbum
 *
 * Casos de prueba:
 * - E2E-HU08-01: Navegación desde Home a pantalla de asociar tracks
 * - E2E-HU08-02: Verificar que los campos del formulario se muestran
 * - E2E-HU08-03: Verificar que se puede seleccionar un álbum
 * - E2E-HU08-04: Verificar que se muestran los tracks existentes
 * - E2E-HU08-05: Verificar navegación de regreso
 */
@RunWith(AndroidJUnit4::class)
class AssociateTracksScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * E2E-HU08-01: Navegación desde Home a pantalla de asociar tracks
     * 
     * Dado que estoy en la pantalla principal
     * Cuando hago clic en "Asociar Tracks"
     * Entonces debo ver la pantalla de asociar tracks con el título correcto
     */
    @Test
    fun testNavigationToAssociateTracksFromHome() {
        // Verificar que estamos en Home
        composeTestRule.onNodeWithText("🎵 VinylHub").assertExists()
        
        // Hacer clic en "Asociar Tracks"
        composeTestRule.onNodeWithText(NAV_ASSOCIATE_TRACKS).performClick()
        
        // Esperar a que se cargue la pantalla
        Thread.sleep(500)
        
        // Verificar que estamos en la pantalla de asociar tracks
        composeTestRule.onNodeWithText(TITLE_ASSOCIATE_TRACKS).assertExists()
    }

    /**
     * E2E-HU08-02: Verificar que el dropdown de álbumes se muestra
     *
     * Dado que estoy en la pantalla de asociar tracks
     * Entonces debo ver el dropdown de álbumes y el botón de volver
     */
    @Test
    fun testAssociateTracksFormFieldsAreDisplayed() {
        // Navegar a la pantalla de asociar tracks
        composeTestRule.onNodeWithText(NAV_ASSOCIATE_TRACKS).performClick()
        Thread.sleep(1000) // Esperar a que se carguen los álbumes

        // Verificar que el dropdown de álbumes existe
        composeTestRule.onNodeWithText(LABEL_SELECT_ALBUM).assertExists()

        // Verificar que el botón de volver existe
        composeTestRule.onNodeWithText(BUTTON_BACK)
            .performScrollTo()
            .assertExists()
    }

    /**
     * E2E-HU08-03: Verificar que se puede seleccionar un álbum
     *
     * Dado que estoy en la pantalla de asociar tracks
     * Cuando selecciono un álbum del dropdown
     * Entonces debo ver la sección de tracks existentes
     */
    @Test
    fun testAlbumSelectionShowsExistingTracks() {
        // Navegar a la pantalla de asociar tracks
        composeTestRule.onNodeWithText(NAV_ASSOCIATE_TRACKS).performClick()
        Thread.sleep(1500) // Esperar a que se carguen los álbumes

        // Hacer clic en el dropdown de álbumes (buscar por el hint text)
        composeTestRule.onNode(hasText("Selecciona un álbum…"))
            .performClick()

        Thread.sleep(500)

        // Seleccionar el primer álbum (Buscando América)
        composeTestRule.onNodeWithText("Buscando América")
            .performClick()

        Thread.sleep(1500) // Esperar a que se carguen los tracks

        // Verificar que se muestra la sección de tracks existentes
        composeTestRule.onNodeWithText(SECTION_EXISTING_TRACKS)
            .performScrollTo()
            .assertExists()
    }

    /**
     * E2E-HU08-04: Verificar que el botón de guardar está habilitado cuando se llenan los campos
     *
     * Dado que estoy en la pantalla de asociar tracks
     * Y he seleccionado un álbum
     * Cuando lleno los campos de título y duración
     * Entonces el botón de guardar debe estar habilitado
     */
    @Test
    fun testSaveButtonEnabledWhenFieldsFilled() {
        // Navegar a la pantalla de asociar tracks
        composeTestRule.onNodeWithText(NAV_ASSOCIATE_TRACKS).performClick()
        Thread.sleep(1500)

        // Seleccionar un álbum
        composeTestRule.onNode(hasText("Selecciona un álbum…"))
            .performClick()
        Thread.sleep(500)

        composeTestRule.onNodeWithText("Buscando América")
            .performClick()
        Thread.sleep(1500)

        // Hacer scroll a la sección de agregar nuevo track
        composeTestRule.onNodeWithText(SECTION_ADD_NEW_TRACK)
            .performScrollTo()

        Thread.sleep(300)

        // Llenar campo de título (buscar por el label)
        composeTestRule.onNode(hasText(LABEL_TRACK_TITLE) and hasSetTextAction())
            .performTextInput("Test Track")

        Thread.sleep(300)

        // Llenar campo de duración
        composeTestRule.onNode(hasText(LABEL_TRACK_DURATION) and hasSetTextAction())
            .performTextInput("3:45")

        Thread.sleep(500)

        // Hacer scroll al botón y verificar que está habilitado
        composeTestRule.onNodeWithText(BUTTON_SAVE_ASSOCIATION)
            .performScrollTo()
            .assertIsEnabled()
    }

    /**
     * E2E-HU08-05: Verificar navegación de regreso
     *
     * Dado que estoy en la pantalla de asociar tracks
     * Cuando hago clic en "Volver"
     * Entonces debo regresar a la pantalla principal
     */
    @Test
    fun testBackNavigationFromAssociateTracks() {
        // Navegar a la pantalla de asociar tracks
        composeTestRule.onNodeWithText(NAV_ASSOCIATE_TRACKS).performClick()
        Thread.sleep(500)

        // Verificar que estamos en la pantalla de asociar tracks
        composeTestRule.onNodeWithText(TITLE_ASSOCIATE_TRACKS).assertExists()

        // Hacer scroll al botón de volver y hacer clic
        composeTestRule.onNodeWithText(BUTTON_BACK)
            .performScrollTo()
            .performClick()

        Thread.sleep(500)

        // Verificar que regresamos a Home
        composeTestRule.onNodeWithText("🎵 VinylHub").assertExists()
    }
}

