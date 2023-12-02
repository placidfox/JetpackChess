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

    private fun squaresMenaced(position: GamePosition): List<Coordinate>{ // to Check if Castle is Possible
        val listPositionsMenaced = emptyList<Coordinate>().toMutableList()
        position.board.piecesColorPositionMinusKing(position.activePlayer.opponent()) // Minus King to Avoid Infinite Loop - not possible to stop castle with King
            .forEach {  // opponent() because turn as been made
                    entry ->
                        entry.value.reachableSquares(position).forEach {
                            listPositionsMenaced.add(it)
                        }
                        if(entry.value::class.java == Pawn::class.java){ // TODO FIND A BETTER WAY TO AVOID CASTLE IF PAWN IS MENACING - NOT CALCULATED IN PAWN BECAUSE NO PIECE
                            when(entry.value.color to entry.key){
                                PlayerColor.WHITE to Coordinate.G7 -> {listPositionsMenaced.add(Coordinate.F8)}
                                PlayerColor.WHITE to Coordinate.E7 -> {
                                    listPositionsMenaced.add(Coordinate.F8)
                                    listPositionsMenaced.add(Coordinate.D8)
                                }
                                PlayerColor.WHITE to Coordinate.C7 -> {listPositionsMenaced.add(Coordinate.D8)}

                                PlayerColor.BLACK to Coordinate.G2 -> {listPositionsMenaced.add(Coordinate.F1)}
                                PlayerColor.BLACK to Coordinate.E2 -> {
                                    listPositionsMenaced.add(Coordinate.F1)
                                    listPositionsMenaced.add(Coordinate.D1)
                                }
                                PlayerColor.BLACK to Coordinate.C2 -> {listPositionsMenaced.add(Coordinate.D1)}
                            }
                        }
            }

        return listPositionsMenaced
    }

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
                    if(!squaresMenaced(position).contains(Coordinate.F1)) { // TODO TO SIMPLIFY
                        val positionTest = pieceLocation + shortCastleTargets
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                }
                if (position.castlingStatus.whiteLongCastlePossible && position.board.getSquare(Coordinate.B1).isEmpty && position.board.getSquare(Coordinate.C1).isEmpty && position.board.getSquare(Coordinate.D1).isEmpty) {
                    if(!squaresMenaced(position).contains(Coordinate.D1)) { // TODO TO SIMPLIFY
                        val positionTest = pieceLocation + longCastleTargets
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                }
            }
            PlayerColor.BLACK -> {
                if (position.castlingStatus.blackShortCastlePossible && position.board.getSquare(Coordinate.F8).isEmpty && position.board.getSquare(Coordinate.G8).isEmpty) {
                    if(!squaresMenaced(position).contains(Coordinate.F8)) { // TODO TO SIMPLIFY
                        val positionTest = pieceLocation + shortCastleTargets
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
                }
                if (position.castlingStatus.blackLongCastlePossible && position.board.getSquare(Coordinate.B8).isEmpty && position.board.getSquare(Coordinate.C8).isEmpty && position.board.getSquare(Coordinate.D8).isEmpty) {
                    if(!squaresMenaced(position).contains(Coordinate.D8)) { // TODO TO SIMPLIFY
                        val positionTest = pieceLocation + longCastleTargets
                        val coordinateTest = Coordinate.fromNumCoordinate(
                            positionTest.toString()[0].digitToInt(),
                            positionTest.toString()[1].digitToInt()
                        )
                        moveSquares.add(coordinateTest)
                    }
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