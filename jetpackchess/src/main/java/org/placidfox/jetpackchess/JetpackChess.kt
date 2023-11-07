package org.placidfox.jetpackchess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.controller.Controller
import org.placidfox.jetpackchess.model.game.parameters.Metadata
import org.placidfox.jetpackchess.model.game.parameters.OpeningMetadata
import org.placidfox.jetpackchess.model.game.parameters.PuzzleMetadata
import org.placidfox.jetpackchess.model.piece.*
import org.placidfox.jetpackchess.ui.board.BoardComposable
import org.placidfox.jetpackchess.ui.control.Arrow
import org.placidfox.jetpackchess.ui.control.PromotionDialog
import org.placidfox.jetpackchess.ui.control.StatusBar
import org.placidfox.jetpackchess.ui.control.SwitchOrientation


@Composable
fun JetpackChess(controller: Controller){

    Column (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        BoardComposable(controller.uiState)
        Arrow(controller.uiState)
        SwitchOrientation(controller.uiState)
        StatusBar(controller.uiState)


        if(controller.uiState.showPromotionDialog.value) {
            PromotionDialog(uiState = controller.uiState)
        }

    }

}


const val FEN_DEFAULT_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
const val FEN_TEST_POSITION = "r3k2r/pp4pp/4R3/8/8/8/P7/R3K2R b KQk - 0 5"
const val FEN_TEST_EN_PASSANT_FIRST = "rnbqkbnr/1pppp1pp/p7/4Pp2/8/8/PPPP1PPP/RNBQKBNR w KQkq f6 0 3"

val testVariationCaroKann = "e2e4 c7c6 d2d4 d7d5 e4d5 c6d5 g1f3 d8c7 f1d3 e8d7 e1g1"
val testVariationPromotion = "e2e4 c7b1R d2c8n d7d5"
val testEnPassant = "e2e4 a7a6 e4e5 a6a5 g2g4 a5a4 g4g5 f7f5 g5f6"
val testEnPassantandCaptureFirst = "e5f6 e7f6"

val puzzlewhite = listOf("2r3k1/1q3ppp/B3pbb1/2Rp4/P7/1Q2P2P/1P4P1/6K1 b - - 2 27", "b7b3 c5c8 f6d8 c8d8")
val puzzleblack = listOf("8/2r3k1/5p2/3Q4/P2P4/4PN2/5PPP/6K1 w - - 3 31", "d5e4 c7c1 f3e1 c1e1")

var mapMetadata = mapOf(
    Metadata.OPENING_NAME to "Caro-Kann",
    Metadata.OPENING_SPECIFIC to "Exchange",

    )
val metadataOpening = OpeningMetadata(mapMetadata)
val metadataPuzzle = PuzzleMetadata(mapMetadata)

val testInitialPieces = mapOf(
    Coordinate.A8 to Rook(PlayerColor.BLACK),
    Coordinate.B8 to Knight(PlayerColor.BLACK),
    Coordinate.C8 to Bishop(PlayerColor.BLACK),
    Coordinate.D8 to Queen(PlayerColor.BLACK),
    Coordinate.E8 to King(PlayerColor.BLACK),
    Coordinate.F8 to Bishop(PlayerColor.BLACK),
    Coordinate.G8 to Knight(PlayerColor.BLACK),
    Coordinate.H8 to Rook(PlayerColor.BLACK),

    Coordinate.A7 to Pawn(PlayerColor.BLACK),
    Coordinate.B7 to Pawn(PlayerColor.BLACK),
    Coordinate.C7 to Pawn(PlayerColor.BLACK),
    Coordinate.D7 to Pawn(PlayerColor.BLACK),
    Coordinate.E7 to Pawn(PlayerColor.BLACK),
    Coordinate.F7 to Pawn(PlayerColor.BLACK),
    Coordinate.G7 to Pawn(PlayerColor.BLACK),
    Coordinate.H7 to Pawn(PlayerColor.BLACK),

    Coordinate.A2 to Pawn(PlayerColor.WHITE),
    Coordinate.B2 to Pawn(PlayerColor.WHITE),
    Coordinate.C2 to Pawn(PlayerColor.WHITE),
    Coordinate.D2 to Pawn(PlayerColor.WHITE),
    Coordinate.E2 to Pawn(PlayerColor.WHITE),
    Coordinate.F2 to Pawn(PlayerColor.WHITE),
    Coordinate.G2 to Pawn(PlayerColor.WHITE),
    Coordinate.H2 to Pawn(PlayerColor.WHITE),

    Coordinate.A1 to Rook(PlayerColor.WHITE),
    Coordinate.B1 to Knight(PlayerColor.WHITE),
    Coordinate.C1 to Bishop(PlayerColor.WHITE),
    Coordinate.D1 to Queen(PlayerColor.WHITE),
    Coordinate.E1 to King(PlayerColor.WHITE),
    Coordinate.F1 to Bishop(PlayerColor.WHITE),
    Coordinate.G1 to Knight(PlayerColor.WHITE),
    Coordinate.H1 to Rook(PlayerColor.WHITE),
)

val pieces1 = mapOf(
    Coordinate.A7 to Pawn(PlayerColor.BLACK),
    Coordinate.B7 to Pawn(PlayerColor.BLACK),
    Coordinate.C7 to Pawn(PlayerColor.BLACK),
    Coordinate.D7 to Pawn(PlayerColor.BLACK),
    Coordinate.E7 to Pawn(PlayerColor.BLACK),
    Coordinate.F7 to Pawn(PlayerColor.BLACK),
    Coordinate.G7 to Pawn(PlayerColor.BLACK),
    Coordinate.H7 to Pawn(PlayerColor.BLACK),

)

val pieces2 = mapOf(

    Coordinate.A1 to Rook(PlayerColor.WHITE),
    Coordinate.B1 to Knight(PlayerColor.WHITE),
    Coordinate.C1 to Bishop(PlayerColor.WHITE),
    Coordinate.D1 to Queen(PlayerColor.WHITE),
    Coordinate.E1 to King(PlayerColor.WHITE),
    Coordinate.F1 to Bishop(PlayerColor.WHITE),
    Coordinate.G1 to Knight(PlayerColor.WHITE),
    Coordinate.H1 to Rook(PlayerColor.WHITE),
)