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

    var activePosition by mutableStateOf(initialGamePosition)
    val uiState = UIState()

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

            if (activePosition.getActivePiecesPosition().contains(square.coordinate)) {
                setSelectedSquare(square.coordinate)
                setMoveSquares()
            }

        } else {
            if (uiState.selectedSquare.contains(square.coordinate)) {
                resetSelectedSquare()
                resetMoveSquares()
            } else {

                if (uiState.moveSquares.contains(square.coordinate)) {

                    proposedMove = ProposedMove(uiState.selectedSquare.first(), square.coordinate, activePosition)

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
        val move = AppliedMove(from, to, activePosition, typePiecePromoteTo)
        val newPosition = calculateNewPosition(activePosition, move)
        addNextMoveToPosition(move)
        gameTimeline.addGamePosition(newPosition)
        forwardActivePosition()
    }






    fun setBoardOrientation(playerColor: PlayerColor){
        uiState.boardOrientation = playerColor
    }
    fun switchBoardOrientation(){
        setBoardOrientation(uiState.boardOrientation.opponent())
    }

    fun changeBoardColor(boardColor: BoardColor){
        uiState.boardColor = boardColor
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
        uiState.showPromotionDialog = !uiState.showPromotionDialog
    }

    private fun setSelectedSquare(coordinate: Coordinate){
        uiState.selectedSquare = listOf(coordinate)
    }

    fun resetSelectedSquare(){
        uiState.selectedSquare = emptyList()
    }

    private fun setMoveSquares(){
        uiState.moveSquares = activePosition.pieceLegalDestinations(uiState.selectedSquare.first())
    }

    fun resetMoveSquares(){
        uiState.moveSquares = emptyList()
    }

    private fun setWrongSquares(){
        // TODO
    }

    fun resetWrongSquares(){
        uiState.wrongChoiceSquares = emptyList()
    }

    fun addNextMoveToPosition(move : AppliedMove){
        activePosition.nextMove = move
    }



    private fun updateActivePosition() {
        activePosition = gameTimeline.positionsTimeline[gameTimeline.activePositionIndex]

        resetSelectedSquare()
        resetMoveSquares()
        resetWrongSquares()

        uiState.isActivePositionFirst = gameTimeline.isActivePositionFirst
        uiState.isActivePositionLast = gameTimeline.isActivePositionLast
    }

}

