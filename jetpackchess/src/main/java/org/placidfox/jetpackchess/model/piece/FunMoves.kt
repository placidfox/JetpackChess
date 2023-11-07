package org.placidfox.jetpackchess.model.piece

import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.game.GamePosition

fun Piece.accessibleSquares(
    chessPosition: GamePosition
): List<Square>{

    val squares = mutableListOf<Square>()

    var pieceCoordinate: Int

    when(this::class){
        Bishop::class ->
            TODO()
        King::class ->
            TODO()
        Knight::class ->
            TODO()

        Pawn::class ->
            TODO()
        Queen::class ->
            TODO()
        Rook::class ->
            TODO()

    }

    fun getAllSquares(){



    }

    return squares
}