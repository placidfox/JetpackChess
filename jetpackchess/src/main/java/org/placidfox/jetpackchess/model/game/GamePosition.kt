package org.placidfox.jetpackchess.model.game

import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.parameters.CastlingStatus
import org.placidfox.jetpackchess.model.game.parameters.EnPassantStatus
import org.placidfox.jetpackchess.model.move.AppliedMove
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor

data class GamePosition(
    val board: Board,
    val activePlayer: PlayerColor,

    val castlingStatus: CastlingStatus,
    val enPassantStatus: EnPassantStatus,

    val halfMoves: Int,
    val nbMove: Int,

    var lastMove: AppliedMove? = null,
    var nextMove: AppliedMove? = null,

    val capturedPieces: List<Piece?> = emptyList(),

    var isActivePlayerKingInCheck: Boolean = false,

    var isActivePlayerKingInCheckmate: Boolean = false,

    var isActivePlayerKingInStalemate: Boolean = false,

    ){
        val lastMovePositions: List<Coordinate>? = lastMove?.let { listOf(it.from, it.to) }

        fun calculateScore(playerColor: PlayerColor): Int =
            when (playerColor){
                PlayerColor.WHITE ->
                    board.piecesColorPosition(playerColor).values.sumOf {it.value} - board.piecesColorPosition(playerColor.opponent()).values.sumOf {it.value}

                PlayerColor.BLACK ->
                    board.piecesColorPosition(playerColor).values.sumOf {it.value} - board.piecesColorPosition(playerColor.opponent()).values.sumOf {it.value}
            }



    }

