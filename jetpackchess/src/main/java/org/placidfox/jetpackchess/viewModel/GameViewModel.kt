package org.placidfox.jetpackchess.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.placidfox.jetpackchess.controller.JetpackChessMode
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.GameTimeline
import org.placidfox.jetpackchess.model.move.AppliedMove
import org.placidfox.jetpackchess.model.move.ProposedMove
import org.placidfox.jetpackchess.model.piece.King
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.board.BoardColor

class GameViewModel(
    val mode: JetpackChessMode,
    val gameTimeline: GameTimeline,
    private val boardColor: BoardColor,
    private val initialUIState: UIState
) {

    var uiState by mutableStateOf(initialUIState)


    var maxSeenPosition: Int = 0

    private var destinationSquare: Coordinate? = null

    val turnPlayerPiecesPositions: List<Coordinate>
        get() = uiState.activePosition.board.piecesColorPosition(uiState.activePlayer).map { it.key }


    private fun resetReachableSquare(){
        uiState = uiState.copy(moveDestinationSquares = emptyList())
    }


    fun clickedSquare(square: Square) {

        when (mode) {
            JetpackChessMode.GAME ->
                if (gameTimeline.activePositionIndex == gameTimeline.positionsTimeline.lastIndex && !gameTimeline.isCheckmate && !gameTimeline.isStalemate) {
                    clickSquareAction(square)
                }
            JetpackChessMode.PUZZLE ->
                if (gameTimeline.activePositionIndex == maxSeenPosition && gameTimeline.activePositionIndex !=gameTimeline.positionsTimeline.lastIndex) {
                    clickSquareAction(square)
                }
            JetpackChessMode.SCROLL -> {}

        }

    }

    fun clickSquareAction(square: Square){
        if (uiState.selectedSquare == null) {

            if (turnPlayerPiecesPositions.contains(square.coordinate)) {
                uiState = uiState.copy(selectedSquare = square.coordinate)
                uiState = uiState.copy(moveDestinationSquares = uiState.activePosition.pieceLegalDestinations(square.coordinate))
            }

        } else {
            if (square.coordinate == uiState.selectedSquare) {
                uiState = uiState.copy(selectedSquare = null)
                resetReachableSquare()
            } else {
                if (uiState.moveDestinationSquares.contains(square.coordinate)) {
                    destinationSquare = square.coordinate

                    val proposedMove = ProposedMove(uiState.selectedSquare!!, destinationSquare!!, uiState.activePosition)

                    if (proposedMove.isPromotionMove){
                        //askPromotion() // TODO
                    } else {
                        validateMove(proposedMove)
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

            JetpackChessMode.PUZZLE ->
                if (proposedMove.from == uiState.activePosition.nextMove!!.from && proposedMove.to == uiState.activePosition.nextMove!!.to && proposedMove.promotionTo == uiState.activePosition.nextMove!!.promotionTo){
                    //resetWrongMoveDecorator()
                    //applyAutoMove()
                } else {
                    //resetSelectedSquare()
                    //setWrongMoveDecorator(proposedMove)
                        //statusMistake()
                }
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
        addMoveMadeGamePosition(move)
        gameTimeline.addGamePosition(newPosition)
    }

    /*
    fun applyAutoMove() {
        forwardActivePosition()

        if (activePositionIndex < gameTimeline.positionsTimeline.lastIndex) // Stop if in last position
            viewModelScope.launch(Dispatchers.IO) {
                delay(500L) // delay before auto-applying the next move
                forwardActivePosition()
            }
    }

    */



    fun addMoveMadeGamePosition(moveUCI: AppliedMove) {
        gameTimeline.positionsTimeline[gameTimeline.positionsTimeline.lastIndex].nextMove = moveUCI // TODO - SIMPLIFY ?
    }


    fun switchOrientation() {
        uiState = uiState.copy(boardOrientation = uiState.boardOrientation.opponent())
    }

    fun switchPromotionDialog(){

        uiState = uiState.copy(showPromotionDialog = !uiState.showPromotionDialog)
    }


    fun initNewTimeline(initPosition: GamePosition) {
        gameTimeline.initNewTimeline(initPosition)
        gameTimeline.changeActivePosition(0)
    }

    /*
    fun initStartActivePosition(index: Int) {
        changeActivePosition(index)
        maxSeenPosition = index
        isMaxSeenPosition.value = false
        updateButtonState()
        initStatus()
    }

    fun changeActivePosition(index: Int) {
        resetSelectedSquare()
        resetReachableSquare()
        resetWrongMoveDecorator()
        activePositionIndex = index
        updateCheckmateAndStalemateStatus()
        checkEndStatus()
    }

    fun forwardActivePosition() {
        changeActivePosition(activePositionIndex + 1)
        updateMaxSeenPosition()
    }

    fun backActivePosition() {
        changeActivePosition(activePositionIndex - 1)
    }

    fun resetSelectedSquare() {
        selectedSquare.value = null
        destinationSquare.value = null
    }

    private fun addGamePositionInTimeline(gamePosition: GamePosition) {
        gameTimeline.addGamePosition(gamePosition)
        forwardActivePosition()
    }

    private fun updateMaxSeenPosition() {
        if (activePositionIndex > maxSeenPosition) {
            maxSeenPosition = activePositionIndex
            isMaxSeenPosition.value = true
        } else {
            isMaxSeenPosition.value = false
        }
    }

    private fun updateActivePosition() {
        activePosition.value = gameTimeline.positionsTimeline[activePositionIndex]
        activePlayer.value = activePosition.value.activePlayer
        score.value = activePosition.value.calculateScore(boardOrientation) // copy from getter() ?
        updateButtonState()
    }


     */

}