package org.placidfox.jetpackchess.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            JetpackChessMode.PUZZLE -> {
                if (gameTimeline.activePositionIndex == gameTimeline.lastSeenPosition && gameTimeline.activePositionIndex !=gameTimeline.positionsTimeline.lastIndex) {
                    clickAction(square)
                }

            }

            JetpackChessMode.SCROLL -> {} // no piece selection in this mode

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
            JetpackChessMode.PUZZLE -> {
                if (proposedMove.from == activePosition.nextMove!!.from && proposedMove.to == activePosition.nextMove!!.to && proposedMove.promotionTo == activePosition.nextMove!!.promotionTo){
                    resetWrongSquares()
                    applyAutoMove()
                } else {
                    resetSelectedSquare()
                    setWrongSquares(proposedMove)
                    setStatusMistake()
                    updateStatus()
                }


            }
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
        updateStatus()
    }

    fun importMove( // TODO Simplify ?
        from: Coordinate,
        to: Coordinate,
        typePiecePromoteTo: Class<out Piece>? = null
    ) {
        val move = AppliedMove(from, to, activePosition, typePiecePromoteTo)
        val newPosition = calculateNewPosition(activePosition, move)
        addNextMoveToPosition(move)
        gameTimeline.addImportPosition(newPosition)
        forwardActivePosition()
    }

    private fun applyAutoMove() {
        forwardActivePosition()

        if (gameTimeline.activePositionIndex < gameTimeline.positionsTimeline.lastIndex) { // Stop if in last position
            viewModelScope.launch(Dispatchers.IO) {
                delay(500L) // delay before auto-applying the next move
                forwardActivePosition()
                if (gameTimeline.activePositionIndex == gameTimeline.positionsTimeline.lastIndex){ // TODO - Refactor 2 time check because activePosition change after coroutine
                    setStatusPuzzleFinish()
                }
            }
        }
        if (gameTimeline.activePositionIndex == gameTimeline.positionsTimeline.lastIndex){
            setStatusPuzzleFinish()
        }

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

    private fun setWrongSquares(proposedMove: ProposedMove){
        resetMoveSquares()
        uiState.wrongChoiceSquares = listOf(proposedMove.from, proposedMove.to)
    }

    fun resetWrongSquares(){
        uiState.wrongChoiceSquares = emptyList()
    }

    fun addNextMoveToPosition(move : AppliedMove){
        activePosition.nextMove = move
    }

    fun initNewTimeline(initPosition: GamePosition) {
        gameTimeline.initNewTimeline(initPosition)
        changeActivePosition(0)
    }

    fun initStartActivePosition(index: Int) {
        gameTimeline.initStartPosition(index)
        updateActivePosition()
    }

    fun updateStatus(){
        uiState.status = gameTimeline.status
    }

    private fun setStatusMistake(){
        gameTimeline.status = STATUS.IN_PROGRESS_WRONG
    }

    private fun setStatusPuzzleFinish(){
        when (gameTimeline.status){
            STATUS.IN_PROGRESS_OK -> gameTimeline.status = STATUS.FINISH_OK
            STATUS.IN_PROGRESS_WRONG -> gameTimeline.status = STATUS.FINISH_WRONG
            else -> {}
        }

        updateStatus()

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

