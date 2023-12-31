package org.placidfox.jetpackchess.model.piece
import org.placidfox.jetpackchess.R
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.toNum
import org.placidfox.jetpackchess.model.board.boardCoordinateNum
import org.placidfox.jetpackchess.model.game.GamePosition

class Bishop(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.b_white
            PlayerColor.BLACK -> R.drawable.b_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♗"
        PlayerColor.BLACK -> "♝"
    }

    override val textSymbol: String = "B"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "B"
            PlayerColor.BLACK -> "b"
        }

    override val value: Int = 3

    override fun reachableSquares(position: GamePosition): List<Coordinate> {

        val moveSquares = emptyList<Coordinate>().toMutableList()

        val pieceLocation = position.board.findSquare(this)!!.coordinate.coordinateInt
        val sameColorPiecesLocation = position.board.piecesColorPosition(this.color).keys.toList().map { it.toNum() }

        directions.forEach {
            var positionTest = pieceLocation + it

            while (positionTest in boardCoordinateNum) {

                val coordinateTest = Coordinate.fromNumCoordinate(positionTest.toString()[0].digitToInt(), positionTest.toString()[1].digitToInt())

                if (position.board.isOccupied(coordinateTest) && positionTest !in sameColorPiecesLocation) {
                    moveSquares.add(coordinateTest)
                    break
                }
                if (positionTest in sameColorPiecesLocation) {
                    break
                }

                moveSquares.add(coordinateTest)
                positionTest += it

            }

        }


        return moveSquares
    }
        companion object {
        val directions = listOf(
            -9, +9,
            -11, +11
        )
    }


}