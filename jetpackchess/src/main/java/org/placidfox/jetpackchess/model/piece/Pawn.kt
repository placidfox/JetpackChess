package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.toNum
import org.placidfox.jetpackchess.model.board.boardCoordinateNum
import org.placidfox.jetpackchess.model.game.GamePosition

class Pawn(override val color: PlayerColor) : Piece {


    override val asset: Int =
        when (color) {
            PlayerColor.WHITE -> R.drawable.p_white
            PlayerColor.BLACK -> R.drawable.p_black
        }

    override val assetRatio: Float = 0.9f

    override val imgSymbol: String = when (color) {
        PlayerColor.WHITE -> "♙"
        PlayerColor.BLACK -> "♟︎"
    }

    override val textSymbol: String = ""

    override val FENSymbol: String =
        when(color) {
            PlayerColor.WHITE -> "P"
            PlayerColor.BLACK -> "p"
        }

    override val value: Int = 1

    override fun reachableSquares(position: GamePosition): List<Coordinate> {

        val moveSquares = emptyList<Coordinate>().toMutableList()

        val pieceLocation = position.board.findSquare(this)!!.coordinate.coordinateInt

        val sameColorPiecesLocation = position.board.piecesColorPosition(this.color).keys.toList().map { it.toNum() }
        val opponentColorPiecesLocation = position.board.piecesColorPosition(this.color.opponent()).keys.toList().map { it.toNum() }

        when(this.color){ // TODO TO SIMPLIFY 1 LOOP ONLY ?
            PlayerColor.WHITE -> {
                if (pieceLocation % 10 == 2) { // TODO TO RANK CHECK INSTEAD
                    targetInitRank.forEach {
                        val positionTest = pieceLocation + it
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )

                        if (!position.board.isOccupied(coordinateTest) && positionTest in boardCoordinateNum && pieceLocation + target[0] !in sameColorPiecesLocation + opponentColorPiecesLocation) { // TODO  SIMPLIFY same color + opponent color
                            moveSquares.add(coordinateTest)
                        }
                    }
                }


                target.forEach {
                        val positionTest = pieceLocation + it
                        if (positionTest in boardCoordinateNum) { // to avoir crash when creating coordinateTest in fromNumCoordinate if is outside of board

                            val coordinateTest = Coordinate.fromNumCoordinate(
                                positionTest.toString()[0].digitToInt(),
                                positionTest.toString()[1].digitToInt()
                            )

                            if (!position.board.isOccupied(coordinateTest)) {
                                moveSquares.add(coordinateTest)
                            }
                        }
                }



                captureTargets.forEach{

                    val positionTest = pieceLocation + it


                    if (positionTest in opponentColorPiecesLocation){
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                    if (positionTest == position.enPassantStatus.enPassantCoordinate?.toNum()) {
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                }


            }
            PlayerColor.BLACK -> {
                if (pieceLocation % 10 == 7) { // TODO TO RANK CHECK INSTEAD
                    targetInitRank.forEach {
                        val positionTest = pieceLocation - it
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )

                        if (!position.board.isOccupied(coordinateTest) && positionTest in boardCoordinateNum && pieceLocation - target[0] !in sameColorPiecesLocation + opponentColorPiecesLocation) { // TODO  SIMPLIFY same color + opponent color
                            moveSquares.add(coordinateTest)
                        }
                    }
                }

                target.forEach {
                        val positionTest = pieceLocation - it
                        if (positionTest in boardCoordinateNum) { // to avoir crash when creating coordinateTest in fromNumCoordinate if is outside of board

                            val coordinateTest = Coordinate.fromNumCoordinate(
                                positionTest.toString()[0].digitToInt(),
                                positionTest.toString()[1].digitToInt()
                            )

                            if (!position.board.isOccupied(coordinateTest)) {
                                moveSquares.add(coordinateTest)
                            }
                        }

                }


                captureTargets.forEach {
                    val positionTest = pieceLocation - it

                    if (positionTest in opponentColorPiecesLocation) {
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                    if (positionTest == position.enPassantStatus.enPassantCoordinate?.toNum()) {
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                }


            }
        }

        return moveSquares // +captureMoveSquares specific to Pawn (can move only if is a capture)
    }

    companion object {

        val targetInitRank = listOf(
            +2
        )

        val target = listOf(
            +1,
        )

        val captureTargets = listOf(
            -9, +11
        )
    }


}