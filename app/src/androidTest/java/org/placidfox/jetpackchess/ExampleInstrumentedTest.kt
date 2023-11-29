package org.placidfox.jetpackchess

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.placidfox.jetpackchess.controller.GameController
import org.placidfox.jetpackchess.model.board.Coordinate

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.placidfox.jetpackchess", appContext.packageName)
    }
}

val controller = GameController()

class TestUI {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testCastleStatusCheck() {

        composeTestRule.setContent {

            JetpackChess(controller)
        }

        composeTestRule.onNodeWithTag("e2").performClick()
        composeTestRule.onNodeWithTag("e4").performClick()

        composeTestRule.onNodeWithTag("d7").performClick()
        composeTestRule.onNodeWithTag("d6").performClick()

        composeTestRule.onNodeWithTag("e1").performClick()
        composeTestRule.onNodeWithTag("e2").performClick()

        composeTestRule.onNodeWithTag("c8").performClick()
        composeTestRule.onNodeWithTag("g4").performClick()


        assert(!controller.viewModel.activePosition.board.isOccupied(Coordinate.E1))
        assert(!controller.viewModel.activePosition.castlingStatus.whiteShortCastlePossible)
        assert(!controller.viewModel.activePosition.castlingStatus.whiteLongCastlePossible)
        assert(controller.viewModel.activePosition.castlingStatus.blackShortCastlePossible)
        assert(controller.viewModel.isKingCheck)

    }



}

class TestUICastleRookMoved {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testCastleStatusCheck() {

        composeTestRule.setContent {

            JetpackChess(controller)
        }

        composeTestRule.onNodeWithTag("e2").performClick()
        composeTestRule.onNodeWithTag("e4").performClick()

        composeTestRule.onNodeWithTag("d7").performClick()
        composeTestRule.onNodeWithTag("d6").performClick()

        composeTestRule.onNodeWithTag("g1").performClick()
        composeTestRule.onNodeWithTag("f3").performClick()

        composeTestRule.onNodeWithTag("e7").performClick()
        composeTestRule.onNodeWithTag("e6").performClick()

        composeTestRule.onNodeWithTag("f1").performClick()
        composeTestRule.onNodeWithTag("b5").performClick()

        composeTestRule.onNodeWithTag("c8").performClick()
        composeTestRule.onNodeWithTag("d7").performClick()

        composeTestRule.onNodeWithTag("h1").performClick()
        composeTestRule.onNodeWithTag("f1").performClick()

        assert(!controller.viewModel.activePosition.board.isOccupied(Coordinate.H1))
        assert(!controller.viewModel.activePosition.castlingStatus.whiteShortCastlePossible)
        assert(controller.viewModel.activePosition.castlingStatus.whiteLongCastlePossible)
        assert(controller.viewModel.activePosition.castlingStatus.blackShortCastlePossible)
        assert(!controller.viewModel.isKingCheck)

    }




}