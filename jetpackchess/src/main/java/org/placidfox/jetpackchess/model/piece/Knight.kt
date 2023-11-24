package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.toNum
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.board.positions
import org.placidfox.jetpackchess.model.game.GamePosition

class Knight(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.n_white
            PlayerColor.BLACK -> R.drawable.n_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♘"
        PlayerColor.BLACK -> "♞"
    }

    override val textSymbol: String = "N"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "N"
            PlayerColor.BLACK -> "n"
        }

    override val value: Int = 3

    override fun reachableSqCoordinates(position: GamePosition): Pair<List<Coordinate>, List<Coordinate>> {

        val reachableSquares = emptyList<Coordinate>().toMutableList()
        val captureMoveSquares = emptyList<Coordinate>().toMutableList()

        val piecePosition = position.board.findSquare(this)!!.coordinate.position
        val sameColorPiecesPosition = position.board.piecesColorPosition(this.color).keys.toList().map { it.toNum() }

        targets.forEach {
            val positionTest = piecePosition + it

            if (positionTest in positions && positionTest !in sameColorPiecesPosition ) {

                val coordinateTest = Coordinate.fromNumCoordinate(positionTest.toString()[0].digitToInt(), positionTest.toString()[1].digitToInt())
                reachableSquares.add(coordinateTest)

                if(position.board.isOccupied(coordinateTest)) {
                    captureMoveSquares.add(coordinateTest)
                }
            }
        }


        return reachableSquares to captureMoveSquares
    }

    companion object {
        val targets = listOf(
            -8,
            +8,
            +12,
            -12,
            -19,
            +19,
            -21,
            +21
        )
    }
}