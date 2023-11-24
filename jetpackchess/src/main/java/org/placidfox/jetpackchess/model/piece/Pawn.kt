package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.R
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.toNum
import org.placidfox.jetpackchess.model.board.positions
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

    override fun reachableSqCoordinates(position: GamePosition): Pair<List<Coordinate>, List<Coordinate>> {

        val reachableSquares = emptyList<Coordinate>().toMutableList()
        val captureMoveSquares = emptyList<Coordinate>().toMutableList()

        val piecePosition = position.board.findSquare(this)!!.coordinate.position

        val sameColorPiecesPosition = position.board.piecesColorPosition(this.color).keys.toList().map { it.toNum() }
        val opponentColorPiecesPosition = position.board.piecesColorPosition(this.color.opponent()).keys.toList().map { it.toNum() }

        when(this.color){ // TODO TO SIMPLIFY 1 LOOP ONLY ?
            PlayerColor.WHITE -> {
                if (piecePosition % 10 == 2) { // TODO TO RANK CHECK INSTEAD
                    targetInitRank.forEach {
                        val positionTest = piecePosition + it
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )

                        if (!position.board.isOccupied(coordinateTest) && positionTest in positions && piecePosition + target[0] !in sameColorPiecesPosition + opponentColorPiecesPosition) { // TODO  SIMPLIFY same color + opponent color
                            reachableSquares.add(coordinateTest)
                        }
                    }
                }


                target.forEach {
                        val positionTest = piecePosition + it
                        val coordinateTest = Coordinate.fromNumCoordinate(positionTest.toString()[0].digitToInt(), positionTest.toString()[1].digitToInt())

                        if (positionTest in positions && (!position.board.isOccupied(coordinateTest))) {
                            reachableSquares.add(coordinateTest)
                        }
                }



                captureTargets.forEach{

                    val positionTest = piecePosition + it


                    if (positionTest in opponentColorPiecesPosition){
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        captureMoveSquares.add(coordinateTest)
                    }
                    if (positionTest == position.enPassantStatus.enPassantCoordinate?.toNum()) {
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        captureMoveSquares.add(coordinateTest)
                    }
                }


            }
            PlayerColor.BLACK -> {
                if (piecePosition % 10 == 7) { // TODO TO RANK CHECK INSTEAD
                    targetInitRank.forEach {
                        val positionTest = piecePosition - it
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )

                        if (!position.board.isOccupied(coordinateTest) && positionTest in positions && piecePosition - target[0] !in sameColorPiecesPosition + opponentColorPiecesPosition) { // TODO  SIMPLIFY same color + opponent color
                            reachableSquares.add(coordinateTest)
                        }
                    }
                }

                target.forEach {
                        val positionTest = piecePosition - it
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )

                        if (positionTest in positions && (!position.board.isOccupied(coordinateTest))) {
                            reachableSquares.add(coordinateTest)
                        }
                }


                captureTargets.forEach {
                    val positionTest = piecePosition - it

                    if (positionTest in opponentColorPiecesPosition) {
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        captureMoveSquares.add(coordinateTest)
                    }
                    if (positionTest == position.enPassantStatus.enPassantCoordinate?.toNum()) {
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        captureMoveSquares.add(coordinateTest)
                    }
                }


            }
        }

        return reachableSquares + captureMoveSquares to captureMoveSquares // +captureMoveSquares specific to Pawn (can move only if is a capture)
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