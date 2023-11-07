package org.placidfox.jetpackchess.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
import org.placidfox.jetpackchess.model.piece.*
import org.placidfox.jetpackchess.ui.board.BoardColor

class UIViewModel (
    private val mode: JetpackChessMode,
    val gameTimeline: GameTimeline,
    private val boardOrientation : PlayerColor,
    private val boardColor: BoardColor,
    //val toolbarState: ToolbarState = ToolbarState,
): ViewModel() {

    private var activePositionIndex: Int = 0
        set(value) {
            field = value
            updateActivePosition()
        }

    private var maxSeenPosition: Int = 0

    val activePosition: MutableState<GamePosition> = mutableStateOf(gameTimeline.positionsTimeline[activePositionIndex])

    val isActivePositionFirst: MutableState<Boolean> = mutableStateOf(false)
    val isActivePositionLast: MutableState<Boolean> = mutableStateOf(false)

    val isFirstPosition: MutableState<Boolean> = mutableStateOf(false)
    val isLastPosition: MutableState<Boolean> = mutableStateOf(false)

    val isMaxSeenPosition: MutableState<Boolean> = mutableStateOf(false)

    var activePlayer = mutableStateOf(PlayerColor.WHITE)

    val boardOrientationState = mutableStateOf(boardOrientation)
    val boardColorState = mutableStateOf(boardColor)

    val selectedSquare = mutableStateOf<Coordinate?>(null)
    val destinationSquare = mutableStateOf<Coordinate?>(null)

    val lastMovePosition: List<Coordinate>?
        get() = activePosition.value.lastMove?.let { listOf(it.from, activePosition.value.lastMove!!.to) }

    // calculate score from boardOrientation
    val score = mutableStateOf(activePosition.value.calculateScore(boardOrientation))


    val turnPlayerPiecesPositions: List<Coordinate>
        get() = activePosition.value.board.piecesColorPosition(activePlayer.value).map { it.key }



    val possibleDestination: List<Coordinate>
        get() = activePosition.value.board.emptySquares() + activePosition.value.board.piecesColorPositionMinusKing(activePlayer.value.opponent()).map { it.key }
        //TODO() // activePlayer Coordinate - pin - check ?

    val selectablePosition: List<Coordinate> = emptyList()
    //TODO() // activePlayer Coordinate - pin - check ?


    fun calculatePossibleDestination() { //TODO() with Move Validation ? activePlayer Coordinate + possible move from piece without pin or check ?


    }


    val showPromotionDialog = mutableStateOf(false)


    var proposedMove: ProposedMove? = null

    fun clickedSquare(square: Square) {

        when (mode) {
            JetpackChessMode.GAME ->
                if (activePositionIndex == gameTimeline.positionsTimeline.lastIndex) {
                    clickSquareAction(square)
                }
            JetpackChessMode.PUZZLE, JetpackChessMode.OPENING_TEST  ->
                if (activePositionIndex == maxSeenPosition && activePositionIndex !=gameTimeline.positionsTimeline.lastIndex) {
                    clickSquareAction(square)
                }
            JetpackChessMode.OPENING_SCROLL -> {}

        }

    }

    fun clickSquareAction(square: Square){
        if (selectedSquare.value == null) {

            if (turnPlayerPiecesPositions.contains(square.position)) {
                selectedSquare.value = square.position

            }

        } else {
            if (square.position == selectedSquare.value) {
                selectedSquare.value = null

            } else {
                if (possibleDestination.contains(square.position)) {
                    destinationSquare.value = square.position

                    proposedMove = ProposedMove(selectedSquare.value!!, destinationSquare.value!!, activePosition.value)

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
            JetpackChessMode.GAME -> // TODO (IF LEGAL ?? + CASTLE POSSIBLE
                applyMove(proposedMove.from, proposedMove.to, proposedMove.promotionTo)

            JetpackChessMode.PUZZLE, JetpackChessMode.OPENING_TEST ->
                if (proposedMove.from == activePosition.value.nextMove!!.from && proposedMove.to == activePosition.value.nextMove!!.to && proposedMove.promotionTo == activePosition.value.nextMove!!.promotionTo){
                    applyAutoMove()

                } else {
                    resetSelectedSquare()
                }
            JetpackChessMode.OPENING_SCROLL -> {}

        }


    }

    fun applyMove(
        from: Coordinate,
        to: Coordinate,
        typePiecePromoteTo: Class<out Piece>? = null
    ) {
        val move = AppliedMove(from, to, activePosition.value, typePiecePromoteTo)
        addGamePositionInTimeline(calculateNewPosition(activePosition.value, move))
    }

    fun applyAutoMove() {
        forwardActivePosition()

        if (activePositionIndex < gameTimeline.positionsTimeline.lastIndex) // AVOID BUG IF OWN MOVE WAS LAST{
            viewModelScope.launch(Dispatchers.IO) {
                delay(500L) // delay the show fo next move
                forwardActivePosition()
            }
    }




    fun switchOrientation() {
        boardOrientationState.value = boardOrientationState.value.opponent()
    }

    fun switchPromotionDialog(){
        showPromotionDialog.value = !showPromotionDialog.value
    }

    fun initNewTimeline(initPosition: GamePosition) {
        gameTimeline.initNewTimeline(initPosition)
        changeActivePosition(0)
    }

    fun initStartActivePosition(index: Int) {
        changeActivePosition(index)
        maxSeenPosition = index
        isMaxSeenPosition.value = false
    }

    fun changeActivePosition(index: Int) {
        resetSelectedSquare()
        activePositionIndex = index
    }

    fun forwardActivePosition() {
        resetSelectedSquare()
        changeActivePosition(activePositionIndex + 1)
        updateMaxSeenPosition()
    }

    fun backActivePosition() {
        resetSelectedSquare()
        changeActivePosition(activePositionIndex - 1)
        updateMaxSeenPosition()

    }

    private fun resetSelectedSquare() {
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
        isActivePositionLast.value = activePositionIndex < gameTimeline.positionsTimeline.lastIndex
        isActivePositionFirst.value = activePositionIndex > 0
        isFirstPosition.value = (activePositionIndex == 0)
        isLastPosition.value = (activePositionIndex == gameTimeline.positionsTimeline.lastIndex)
        score.value = activePosition.value.calculateScore(boardOrientation) // copy from getter() ?
    }

}





