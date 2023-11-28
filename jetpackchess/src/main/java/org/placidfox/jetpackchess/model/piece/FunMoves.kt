package org.placidfox.jetpackchess.model.piece

import android.icu.text.Transliterator.Position
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.toNum

import org.placidfox.jetpackchess.model.board.positions
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.piece.Pawn.Companion.captureTargets
import org.placidfox.jetpackchess.model.piece.Pawn.Companion.targetInitRank

var init = 0

fun Piece.reachableSqCoordinates(    // List of square where the piece can move (without check validation)
    position: GamePosition
): Pair<List<Coordinate>,List<Coordinate>>{

    val reachableSquares = emptyList<Coordinate>().toMutableList()
    val captureMoveSquares = emptyList<Coordinate>().toMutableList()

    val piecePosition = position.board.findSquare(this)!!.coordinate.position

    val sameColorPiecesPosition = position.board.piecesColorPosition(this.color).keys.toList().map { it.toNum() }
    val opponentColorPiecesPosition = position.board.piecesColorPosition(this.color.opponent()).keys.toList().map { it.toNum() }

    val listPositions = emptyList<Int>().toMutableList()

    when(this::class) {
        Bishop::class ->
            Bishop.directions.forEach {
                var positionTest = piecePosition + it

                while (positionTest in positions) {

                    if (positionTest in opponentColorPiecesPosition) {
                        listPositions.add(positionTest)
                        break
                    }
                    if (positionTest in sameColorPiecesPosition) {
                        break
                    }

                    listPositions.add(positionTest)
                    positionTest += it

                }

            }

        King::class -> {
            King.targets.forEach {
                val positionTest = piecePosition + it

                if (positionTest in positions && positionTest !in sameColorPiecesPosition ) {
                    listPositions.add(positionTest)
                }
            }
            when(this.color){
                PlayerColor.WHITE -> {
                    if (position.castlingStatus.whiteShortCastlePossible && position.board.getSquare(Coordinate.F1).isEmpty && position.board.getSquare(Coordinate.G1).isEmpty) {
                        listPositions.add(piecePosition + King.shortCastleTargets)
                    }
                    if (position.castlingStatus.whiteLongCastlePossible && position.board.getSquare(Coordinate.B1).isEmpty && position.board.getSquare(Coordinate.C1).isEmpty && position.board.getSquare(Coordinate.D1).isEmpty) {
                        listPositions.add(piecePosition + King.longCastleTargets)
                    }
                }
                PlayerColor.BLACK -> {
                    if (position.castlingStatus.blackShortCastlePossible && position.board.getSquare(Coordinate.F8).isEmpty && position.board.getSquare(Coordinate.G8).isEmpty) {
                        listPositions.add(piecePosition + King.shortCastleTargets)
                    }
                    if (position.castlingStatus.blackLongCastlePossible && position.board.getSquare(Coordinate.B8).isEmpty && position.board.getSquare(Coordinate.C8).isEmpty && position.board.getSquare(Coordinate.D8).isEmpty) {
                        listPositions.add(piecePosition + King.longCastleTargets)
                    }
                }
            }

        }
        Knight::class ->
            Knight.targets.forEach {
                val positionTest = piecePosition + it

                if (positionTest in positions && positionTest !in sameColorPiecesPosition ) {
                    listPositions.add(positionTest)
                }
            }

        Pawn::class -> { // TODO SIMPLIFY ?
            when(this.color){
                PlayerColor.WHITE -> {
                    if (piecePosition % 10  == 2){ // TODO TO RANK CHECK INSTEAD
                        targetInitRank.forEach{
                            val positionTest = piecePosition + it

                            if (positionTest in positions && (positionTest !in sameColorPiecesPosition + opponentColorPiecesPosition) && (piecePosition + Pawn.target[0] !in sameColorPiecesPosition + opponentColorPiecesPosition)) {
                                listPositions.add(positionTest)
                            }
                        }
                    }

                    Pawn.target.forEach{
                        val positionTest = piecePosition + it

                        if (positionTest in positions && (positionTest !in sameColorPiecesPosition + opponentColorPiecesPosition)) {
                            listPositions.add(positionTest)
                        }
                    }


                    captureTargets.forEach{
                        val positionTest = piecePosition + it
                        if (positionTest in opponentColorPiecesPosition){
                            listPositions.add(positionTest)
                        }
                        if (positionTest == position.enPassantStatus.enPassantCoordinate?.toNum())    {
                            listPositions.add(positionTest)
                        }
                    }
                }
                PlayerColor.BLACK -> {
                    if (piecePosition % 10  == 7){ // TODO TO RANK CHECK INSTEAD
                        targetInitRank.forEach{
                            val positionTest = piecePosition - it

                            if (positionTest in positions && (positionTest !in sameColorPiecesPosition + opponentColorPiecesPosition) && (piecePosition - Pawn.target[0] !in sameColorPiecesPosition + opponentColorPiecesPosition)) {
                                listPositions.add(positionTest)
                            }
                        }
                    }

                    Pawn.target.forEach{
                        val positionTest = piecePosition - it

                        if (positionTest in positions && (positionTest !in sameColorPiecesPosition + opponentColorPiecesPosition)) {
                            listPositions.add(positionTest)
                        }
                    }

                    captureTargets.forEach{
                        val positionTest = piecePosition - it
                        if (positionTest in opponentColorPiecesPosition){
                            listPositions.add(positionTest)
                        }
                        if (positionTest == position.enPassantStatus.enPassantCoordinate?.toNum())    {
                            listPositions.add(positionTest)
                        }
                    }
                }
            }

        }



        Queen::class ->
            Queen.directions.forEach {
                var positionTest = piecePosition + it

                while (positionTest in positions) {

                    if (positionTest in opponentColorPiecesPosition) {
                        listPositions.add(positionTest)
                        break
                    }
                    if (positionTest in sameColorPiecesPosition) {
                        break
                    }

                    listPositions.add(positionTest)
                    positionTest += it

                }

            }
        Rook::class ->
            Rook.directions.forEach {
                var positionTest = piecePosition + it

                while (positionTest in positions) {

                    if (positionTest in opponentColorPiecesPosition) {
                        listPositions.add(positionTest)
                        break
                    }
                    if (positionTest in sameColorPiecesPosition) {
                        break
                    }

                    listPositions.add(positionTest)
                    positionTest += it

                }

            }
    }

    listPositions.forEach {
        reachableSquares.add(Coordinate.fromNumCoordinate(it.toString()[0].digitToInt(), it.toString()[1].digitToInt()))
    }

    reachableSquares.forEach {
        if(position.board.isOccupied(it)) {
            captureMoveSquares.add(it)
        }

        if (this::class == Pawn::class){
            if (position.enPassantStatus.enPassantCoordinate?.toNum() == it.position){
                captureMoveSquares.add(it)
            }
        }


    }

    return reachableSquares to captureMoveSquares
}


fun Piece.canKingBeCaptured(position: GamePosition): Boolean{

    var kingCheckedSquare : Boolean = false

    this.reachableSqCoordinates(position).first.forEach {
        if (position.board.isOccupied(it)){ // to avoid crash null pointer if enPassant = getSquare(it).pieces! is empty
            if(position.board.getSquare(it).piece!!::class.java == King::class.java) {
                kingCheckedSquare = true
            }
        }
    }

    return kingCheckedSquare
}

