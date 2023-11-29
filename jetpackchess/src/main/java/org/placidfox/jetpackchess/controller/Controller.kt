package org.placidfox.jetpackchess.controller

import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.GameTimeline
import org.placidfox.jetpackchess.model.game.parameters.*
import org.placidfox.jetpackchess.model.piece.*
import org.placidfox.jetpackchess.ui.board.BoardColor
import org.placidfox.jetpackchess.viewModel.*

interface Controller {
    val mode: JetpackChessMode
    var viewModel: GameViewModel


    fun reset() {
        importFen(FEN_DEFAULT_POSITION)
        viewModel.setBoardOrientation(PlayerColor.WHITE)
    }



    fun importFen(fen: String) { //TODO adapt to new viewModel

        val splitFen = fen.split(" ")
/*
        viewModel.initNewTimeline(
            GamePosition(
                Board(splitFenPosition(splitFen[0])),
                splitPlayerTurn(splitFen[1]),
                splitCastlingPossibilities(splitFen[2]),
                splitEnPassant(splitFen[3]),
                splitHalfMove(splitFen[4]),
                splitMoveNumber(splitFen[5])
            )
        )

        viewModel.changeActivePosition(0)
        */


    }


    fun importFENandMoveList(fen: String, uciMoves: String, startIndex: Int = 0) { //TODO adapt to new viewModel
        importFen(fen)

        val uciMovesString: List<String> =
            uciMoves.split(" ")

        for (move in uciMovesString) {
            val from = move.substring(startIndex = 0, endIndex = 2)
            val to = move.substring(startIndex = 2, endIndex = 4)
            var piecePromote: Class<out Piece>? = null

            if (move.length > 4) {
                when (move.substring(startIndex = 4, endIndex = 5)) {
                    "r" -> piecePromote = Rook::class.java
                    "n" -> piecePromote = Knight::class.java
                    "b" -> piecePromote = Bishop::class.java
                    "q" -> piecePromote = Queen::class.java

                    "R" -> piecePromote = Rook::class.java
                    "N" -> piecePromote = Knight::class.java
                    "B" -> piecePromote = Bishop::class.java
                    "Q" -> piecePromote = Queen::class.java
                }
            }

            //viewModel.applyMove(Coordinate.fromString(from), Coordinate.fromString(to), piecePromote)
        }

        //viewModel.initStartActivePosition(startIndex)

    }


}
class GameController : Controller {

    override val mode: JetpackChessMode = JetpackChessMode.GAME
    override var viewModel: GameViewModel = GameViewModel(
        mode,
        GameTimeline(mode, mutableListOf(initialGamePosition)),
    )


    fun newGame(
        playerSide: PlayerColor,
        initialFEN: String = FEN_DEFAULT_POSITION,
    ) {
        importFen(initialFEN)
        viewModel.setBoardOrientation(playerSide)
    }


}

class PuzzleController : Controller {

    override val mode: JetpackChessMode = JetpackChessMode.PUZZLE
    override var viewModel: GameViewModel = GameViewModel(
        mode,
        GameTimeline(mode, mutableListOf(initialGamePosition)),
    )

    fun newPuzzle(
        fen: String,
        uciMoves: String,
        playerSide: PlayerColor,
        firstDisplayedMove: Int = 0
    ){
        importFENandMoveList(
            fen,
            uciMoves,
            firstDisplayedMove
        )
        viewModel.setBoardOrientation(playerSide)
    }

    fun newPuzzleLichess(
        fen: String,
        uciMoves: String
    ){
        val turnFirstMoveVariation = splitPlayerTurn(fen.split(" ")[1])

        importFENandMoveList(
            fen,
            uciMoves,
            1
        )
        viewModel.setBoardOrientation(turnFirstMoveVariation.opponent())
    }

}


class ScrollController : Controller {

    override val mode: JetpackChessMode = JetpackChessMode.SCROLL
    override var viewModel: GameViewModel = GameViewModel(
        mode,
        GameTimeline(mode, mutableListOf(initialGamePosition)),
    )

    fun newVariation(
        fen: String,
        uciMoves: String,
        playerSide: PlayerColor
    ){
        importFENandMoveList(
            fen,
            uciMoves
        )
        viewModel.setBoardOrientation(playerSide)
    }


}

enum class JetpackChessMode{
    GAME,
    PUZZLE,
    SCROLL
}

const val FEN_DEFAULT_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

val initialPieces = mapOf(
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

val initialGamePosition = GamePosition(
    Board(initialPieces),
    PlayerColor.WHITE,
    CastlingStatus(true, true, true, true),
    EnPassantStatus(false, null),
    0,
    1
)


