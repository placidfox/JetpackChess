package org.placidfox.jetpackchess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.placidfox.jetpackchess.controller.FEN_DEFAULT_POSITION
import org.placidfox.jetpackchess.controller.GameController
import org.placidfox.jetpackchess.controller.PuzzleController
import org.placidfox.jetpackchess.controller.ScrollController
import org.placidfox.jetpackchess.model.game.STATUS
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.theme.JetpackChessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //val controller = GameController()
        //controller.newGame(PlayerColor.WHITE)

        val controller = PuzzleController()
        controller.newPuzzleLichess(puzzlewhitepromotion[0], puzzlewhitepromotion[1])


        //val controller = ScrollController()
        //controller.newVariation(puzzlewhitepromotion[0], puzzlewhitepromotion[1], PlayerColor.WHITE)


        super.onCreate(savedInstanceState)
        setContent {
            JetpackChessTheme {


                Surface(modifier = Modifier.fillMaxSize()) {

                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally)
                    {

                        JetpackChess(controller)

                        Button(onClick = {  controller.reset()}) {
                            Text("Reset")
                        }

                        Text(controller.viewModel.uiState.status.toString())


                        /// TEST CAPTURED PIECES
                        Text(controller.viewModel.activePosition.capturedPieces.toString())

                        /// TEST SCORES
                        Text(controller.viewModel.uiState.score.toString())



                        /*
                        Button(
                            onClick = { controller.viewModel.showHintSquare()},
                            enabled = controller.viewModel.uiState.hintSquareButtonActive
                        ) {
                            Text("Hint")
                        }
                        */

                        /*
                        Button(
                            colors =
                            when(controller.viewModel.uiState.status){
                                STATUS.PENDING -> ButtonDefaults.buttonColors(Color.Gray)
                                STATUS.SCROLLING -> ButtonDefaults.buttonColors(Color.Gray) //TODO()
                                STATUS.IN_PROGRESS_GAME -> ButtonDefaults.buttonColors(Color.Gray) //TODO()
                                STATUS.IN_PROGRESS_OK -> ButtonDefaults.buttonColors(Color.Yellow)
                                STATUS.IN_PROGRESS_WRONG -> ButtonDefaults.buttonColors(Color.Red)
                                STATUS.FINISH_CHECKMATE -> ButtonDefaults.buttonColors(Color.Gray) //TODO()
                                STATUS.FINISH_STALEMATE -> ButtonDefaults.buttonColors(Color.Gray) //TODO()
                                STATUS.FINISH_OK -> ButtonDefaults.buttonColors(Color.Green)
                                STATUS.FINISH_WRONG -> ButtonDefaults.buttonColors(Color.Red)
                            }
                            , onClick = {println(controller.viewModel.uiState.status)}){
                        }

                         */
                    }
                }



            }
        }
    }
}


// Test values & variation

val testStalemate = listOf("8/8/8/K1Q5/8/8/8/k7 w - - 2 27", "c5c2")

val puzzlewhite = listOf("2r3k1/1q3ppp/B3pbb1/2Rp4/P7/1Q2P2P/1P4P1/6K1 b - - 2 27", "b7b3 c5c8 f6d8 c8d8")
val puzzleblack = listOf("8/2r3k1/5p2/3Q4/P2P4/4PN2/5PPP/6K1 w - - 3 31", "d5e4 c7c1 f3e1 c1e1")
val puzzlewhitepromotion = listOf("q6k/2P2ppp/8/Q1b5/1P6/B1P3N1/1R3PPP/2R3K1 b - - 0 1", "a8a5 c7c8Q c5f8 c8f8")

val openingwhite: List<String> = listOf(FEN_DEFAULT_POSITION, "e2e4 e7e5 g1f3 b8c6 f1b5 g8f6 e1g1 f6e4")
val openingblack: List<String> = listOf(FEN_DEFAULT_POSITION, "e2e4 c7c6 d2d4 d7d5 e4e5 c6c5")


val testVariationCaroKann = "e2e4 c7c6 d2d4 d7d5 e4d5 c6d5 g1f3 d8c7 f1d3 e8d7 e1g1"
val testVariationPromotion = "e2e4 c7b1R d2c8n d7d5"
val testEnPassant = "e2e4 a7a6 e4e5 a6a5 g2g4 a5a4 g4g5 f7f5 g5f6"
val testEnPassantandCaptureFirst = "e5f6 e7f6"


