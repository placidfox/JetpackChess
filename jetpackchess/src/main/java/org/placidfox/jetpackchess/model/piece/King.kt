package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.toNum
import org.placidfox.jetpackchess.model.board.boardCoordinateNum
import org.placidfox.jetpackchess.model.game.GamePosition

class King(override val color: PlayerColor) : Piece {

    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.k_white
            PlayerColor.BLACK -> R.drawable.k_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♔"
        PlayerColor.BLACK -> "♚"
    }

    override val textSymbol: String = "K"

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "K"
            PlayerColor.BLACK -> "k"
        }

    override val value: Int = 0 // Useless for the King ?


    override fun reachableSquares(position: GamePosition):List<Coordinate> {

        val moveSquares = emptyList<Coordinate>().toMutableList()

        val pieceLocation = position.board.findSquare(this)!!.coordinate.coordinateInt
        val sameColorPiecesLocation = position.board.piecesColorPosition(this.color).keys.toList().map { it.toNum() }

        targets.forEach {
            val positionTest = pieceLocation + it

            if (positionTest in boardCoordinateNum && positionTest !in sameColorPiecesLocation ) {

                val coordinateTest = Coordinate.fromNumCoordinate(positionTest.toString()[0].digitToInt(), positionTest.toString()[1].digitToInt())
                moveSquares.add(coordinateTest)

            }
        }

        when(this.color){ // TODO TO SIMPLIFY
            PlayerColor.WHITE -> {
                if (position.castlingStatus.whiteShortCastlePossible && position.board.getSquare(Coordinate.F1).isEmpty && position.board.getSquare(Coordinate.G1).isEmpty) {
                    moveSquares.add(Coordinate.fromNumCoordinate(pieceLocation + shortCastleTargets.toString()[0].digitToInt(), pieceLocation + shortCastleTargets.toString()[1].digitToInt()))
                }
                if (position.castlingStatus.whiteLongCastlePossible && position.board.getSquare(Coordinate.B1).isEmpty && position.board.getSquare(Coordinate.C1).isEmpty && position.board.getSquare(Coordinate.D1).isEmpty) {
                    moveSquares.add(Coordinate.fromNumCoordinate(pieceLocation + longCastleTargets.toString()[0].digitToInt(), pieceLocation + longCastleTargets.toString()[1].digitToInt()))
                }
            }
            PlayerColor.BLACK -> {
                if (position.castlingStatus.blackShortCastlePossible && position.board.getSquare(Coordinate.F8).isEmpty && position.board.getSquare(Coordinate.G8).isEmpty) {
                    moveSquares.add(Coordinate.fromNumCoordinate(pieceLocation + shortCastleTargets.toString()[0].digitToInt(), pieceLocation + shortCastleTargets.toString()[1].digitToInt()))
                }
                if (position.castlingStatus.blackLongCastlePossible && position.board.getSquare(Coordinate.B8).isEmpty && position.board.getSquare(Coordinate.C8).isEmpty && position.board.getSquare(Coordinate.D8).isEmpty) {
                    moveSquares.add(Coordinate.fromNumCoordinate(pieceLocation + longCastleTargets.toString()[0].digitToInt(), pieceLocation + longCastleTargets.toString()[1].digitToInt()))
                }
            }
        }


        return moveSquares
    }

    companion object {
        val targets = listOf(
            -11,
            +11,
            -10,
            +10,
            -9,
            +9,
            -1,
            +1
        )

        val shortCastleTargets = 20 // TO CHECK - FCT TO TEST IF CASTEL NOT POSSIBLE (CONTROLLED SQUARED INSIDE CASTEL MOVEMENT)

        val longCastleTargets = -20 // TO CHECK - FCT TO TEST IF CASTEL NOT POSSIBLE (CONTROLLED SQUARED INSIDE CASTEL MOVEMENT)
    }



}