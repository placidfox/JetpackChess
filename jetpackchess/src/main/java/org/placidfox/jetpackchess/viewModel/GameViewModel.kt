package org.placidfox.jetpackchess.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.jetbrains.annotations.TestOnly
import org.placidfox.jetpackchess.controller.*
import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.GameTimeline
import org.placidfox.jetpackchess.model.game.STATUS
import org.placidfox.jetpackchess.model.move.AppliedMove
import org.placidfox.jetpackchess.model.move.ProposedMove
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.board.BoardColor

class GameViewModel (
    val mode: JetpackChessMode,
    val gameTimeline: GameTimeline,
): ViewModel() {

    var uiState by mutableStateOf(UIState())

    var proposedMove: ProposedMove? = null

    fun clickSquare(square: Square) {

        when (mode) {
            JetpackChessMode.GAME ->
                if (gameTimeline.activePositionIndex == gameTimeline.positionsTimeline.lastIndex && gameTimeline.status == STATUS.IN_PROGRESS_GAME) {
                    clickAction(square)
                }
            JetpackChessMode.PUZZLE -> {} // TODO

            JetpackChessMode.SCROLL -> {} // TODO

        }

    }

    private fun clickAction(square: Square){
        if (uiState.selectedSquare.isEmpty()) {

            if (uiState.activePosition.getActivePiecesPosition().contains(square.coordinate)) {
                setSelectedSquare(square.coordinate)
                setMoveSquares()
            }

        } else {
            if (uiState.selectedSquare.contains(square.coordinate)) {
                resetSelectedSquare()
                resetMoveSquares()
            } else {

                if (uiState.moveSquares.contains(square.coordinate)) {

                    proposedMove = ProposedMove(uiState.selectedSquare.first(), square.coordinate, uiState.activePosition)

                    if (proposedMove!!.isPromotionMove){
                        askPromotion()
                    } else {
                        validateMove(proposedMove!!)
                    }


                }


            }
        }
    }

    fun validateMove(
        proposedMove: ProposedMove
    ){
        when (mode){
            JetpackChessMode.GAME ->
                applyMove(proposedMove.from, proposedMove.to, proposedMove.promotionTo)
            JetpackChessMode.PUZZLE -> {}
            JetpackChessMode.SCROLL -> {}

        }

    }

    fun applyMove(
        from: Coordinate,
        to: Coordinate,
        typePiecePromoteTo: Class<out Piece>? = null
    ) {
        val move = AppliedMove(from, to, uiState.activePosition, typePiecePromoteTo)
        val newPosition = calculateNewPosition(uiState.activePosition, move)
        addNextMoveToPosition(move)
        gameTimeline.addGamePosition(newPosition)
        forwardActivePosition()
    }






    fun setBoardOrientation(playerColor: PlayerColor){
        uiState = uiState.copy(boardOrientation = playerColor)
    }
    fun switchBoardOrientation(){
        setBoardOrientation(uiState.boardOrientation.opponent())
    }

    fun changeBoardColor(boardColor: BoardColor){
        uiState = uiState.copy(boardColor = boardColor)
    }

    fun changeActivePosition(index: Int){
        gameTimeline.changeActivePosition(index)
        updateActivePosition()
    }

    fun backActivePosition(){
        gameTimeline.backwardActivePosition()
        updateActivePosition()
    }

    fun forwardActivePosition(){
        gameTimeline.forwardActivePosition()
        updateActivePosition()
    }

    fun switchPromotionDialog(){
        uiState = uiState.copy(showPromotionDialog = !uiState.showPromotionDialog)
    }

    private fun setSelectedSquare(coordinate: Coordinate){
        uiState = uiState.copy(selectedSquare = listOf(coordinate))
    }

    fun resetSelectedSquare(){
        uiState = uiState.copy(selectedSquare = emptyList())
    }

    private fun setMoveSquares(){
        uiState = uiState.copy(moveSquares = uiState.activePosition.pieceLegalDestinations(uiState.selectedSquare.first()))
    }

    fun resetMoveSquares(){
        uiState = uiState.copy(moveSquares = emptyList())
    }

    fun addNextMoveToPosition(move : AppliedMove){
        uiState.activePosition.nextMove = move
    }



    private fun updateActivePosition(){
        uiState = uiState.copy(activePosition = gameTimeline.positionsTimeline[gameTimeline.activePositionIndex],
            selectedSquare = emptyList(),
            moveSquares = emptyList(),
            wrongChoiceSquares = emptyList(),
            isActivePositionFirst = gameTimeline.isActivePositionFirst,
            isActivePositionLast = gameTimeline.isActivePositionLast)
    }



}

val testFENMate = "4k3/4Q3/4K3/8/8/8/8/8 b - - 0 1"

val splitFenMate = testFENMate.split(" ")

val positionCheckmate = GamePosition(
    Board(splitFenPosition(splitFenMate[0])),
    splitPlayerTurn(splitFenMate[1]),
    splitCastlingPossibilities(splitFenMate[2]),
    splitEnPassant(splitFenMate[3]),
    splitHalfMove(splitFenMate[4]),
    splitMoveNumber(splitFenMate[5])
)
