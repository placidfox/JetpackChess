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

        /* TODO

        fun isLegalDestination(playerColor: PlayerColor): Boolean {
            val allLegalDestination = emptyList<Coordinate>().toMutableList()

            board.piecesColorPosition(playerColor).forEach {
                entry ->
                    allLegalDestination += pieceAllLegalDestination(entry.value)
            }

            return allLegalDestination.isNotEmpty()
        }

        fun pieceAllLegalDestination(piece: Piece): List<Coordinate> {
            val pieceReachableDestinations = pieceReachableDestinations(piece)
            return applyCheckAndPinnedConstraints(getPieceCoordinate(piece), pieceReachableDestinations.first)
        }

        private fun pieceReachableDestinations(piece: Piece): Pair<List<Coordinate>,List<Coordinate>> {
            return piece.reachableSqCoordinates(this)
        }


        private fun applyCheckAndPinnedConstraints(piecePosition : Coordinate, possibleDestination : List<Coordinate>): List<Coordinate>{
            // Pinned validation
            val unavailablePinnedSquared = emptyList<Coordinate>().toMutableList()

            possibleDestination.forEach {
                val potentialPosition = calculateNewPosition(this, moveUCI = AppliedMove(piecePosition, it, this))

                var calculateOwnKingWillBeCheck: Boolean = false

                val kingCoordinate = board.kingPosition(activePlayer)

                board.piecesColorPosition(activePlayer.opponent()).forEach {  // opponent() because turn as been made
                    entry -> if(entry.value.reachableSqCoordinates(this).second.contains(kingCoordinate)){
                        calculateOwnKingWillBeCheck = true
                    }
                }

                potentialPosition.board.piecesColorPosition(activePlayer.opponent()).forEach {  // opponent() because turn as been made
                        entry -> if(entry.value.canKingBeCaptured(potentialPosition)){
                            calculateOwnKingWillBeCheck = true
                        }
                }

                if (calculateOwnKingWillBeCheck){
                    unavailablePinnedSquared.add(it)
                }
            }


            return possibleDestination - unavailablePinnedSquared.toSet()
        }

        fun getPieceCoordinate(piece: Piece) : Coordinate{
            return board.findSquare(piece)!!.coordinate
        }

         */



}

