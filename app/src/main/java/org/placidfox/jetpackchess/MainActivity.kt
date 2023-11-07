package org.placidfox.jetpackchess

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import org.placidfox.jetpackchess.controller.OpeningScrollController
import org.placidfox.jetpackchess.controller.PuzzleController
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_NAME
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_SPECIFIC
import org.placidfox.jetpackchess.model.game.parameters.OpeningMetadata
import org.placidfox.jetpackchess.model.game.parameters.PuzzleMetadata
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.theme.JetpackChessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val controller = PuzzleController()
        //controller.importFENandMoveList(puzzlewhitepromotion[0], puzzlewhitepromotion[1])
        //controller.importMetadata(metadataPuzzle)

        controller.newPuzzle(puzzlewhitepromotion[0], puzzlewhitepromotion[1], metadataOpening, PlayerColor.WHITE, false)
        //controller.newPuzzleLichess(puzzleblack[0], puzzleblack[1], metadataOpening)

        //controller.newOpening(uciVariation = openingwhite[1], metadata = metadataOpening, playerSide = PlayerColor.WHITE)
        //controller.newOpening(uciVariation = openingblack[1], metadata = metadataOpening, playerSide = PlayerColor.BLACK)


        super.onCreate(savedInstanceState)
        setContent {
            JetpackChessTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {

                    JetpackChess(controller)

                }
            }
        }
    }
}

val puzzlewhite = listOf("2r3k1/1q3ppp/B3pbb1/2Rp4/P7/1Q2P2P/1P4P1/6K1 b - - 2 27", "b7b3 c5c8 f6d8 c8d8")
val puzzleblack = listOf("8/2r3k1/5p2/3Q4/P2P4/4PN2/5PPP/6K1 w - - 3 31", "d5e4 c7c1 f3e1 c1e1")
val puzzlewhitepromotion = listOf("q6k/2P2ppp/8/Q1b5/1P6/B1P3N1/1R3PPP/2R3K1 b - - 0 1", "a8a5 c7c8Q c5f8 c8f8")

val openingwhite: List<String> = listOf(FEN_DEFAULT_POSITION, "e2e4 e7e5 g1f3 b8c6 f1b5 g8f6 e1g1 f6e4")
val openingblack: List<String> = listOf(FEN_DEFAULT_POSITION, "e2e4 c7c6 d2d4 d7d5 e4e5 c6c5")






val testVariationCaroKann = "e2e4 c7c6 d2d4 d7d5 e4d5 c6d5 g1f3 d8c7 f1d3 e8d7 e1g1"
val testVariationPromotion = "e2e4 c7b1R d2c8n d7d5"
val testEnPassant = "e2e4 a7a6 e4e5 a6a5 g2g4 a5a4 g4g5 f7f5 g5f6"
val testEnPassantandCaptureFirst = "e5f6 e7f6"


var mapMetadata = mapOf(
    OPENING_NAME to "Caro-Kann",
    OPENING_SPECIFIC to "Exchange",
)
val metadataOpening = OpeningMetadata(mapMetadata)
val metadataPuzzle = PuzzleMetadata(mapMetadata)

