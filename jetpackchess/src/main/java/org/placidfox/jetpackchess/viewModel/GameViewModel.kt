package org.placidfox.jetpackchess.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import org.placidfox.jetpackchess.model.piece.King
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.board.BoardColor

class GameViewModel (
    val mode: JetpackChessMode,
    val gameTimeline: GameTimeline,
    private val boardOrientation : PlayerColor,
    private val boardColor: BoardColor,
): ViewModel() {

    var activePositionIndex: Int = 0
        set(value) {
            field = value
            updateActivePosition()
        }

    var activePositionIndexState by mutableStateOf(0)
    
    val activePosition
        get() = gameTimeline.positionsTimeline[activePositionIndexState]
        
    
    var maxSeenPosition: Int = 0

    //val activePosition: MutableState<GamePosition> = mutableStateOf(gameTimeline.positionsTimeline[activePositionIndexState])

    var isActivePositionFirst by mutableStateOf(false)
    var isActivePositionLast by mutableStateOf(false)

    var isFirstPosition by mutableStateOf(false)
    var isLastPosition by mutableStateOf(false)

    private val isMaxSeenPosition: MutableState<Boolean> = mutableStateOf(false)

    val status: MutableState<STATUS> = mutableStateOf(STATUS.PENDING)
    val isKingCheck: Boolean
        get() = activePosition.isActiveKingCheck

    val isKingCheckmate: Boolean
        get() = activePosition.isCheckmate

    val isKingStalemate: Boolean
        get() = activePosition.isStalemate

    val activePlayer
        get() = activePosition.activePlayer

    val boardOrientationState = mutableStateOf(boardOrientation)
    val boardColorState = mutableStateOf(boardColor)

    val selectedSquare = mutableStateOf<Coordinate?>(null)
    val destinationSquare = mutableStateOf<Coordinate?>(null)

    val lastMovePosition: List<Coordinate>?
        get() = activePosition.lastMovePositions

    var wrongMovePosition = mutableStateOf<List<Coordinate>?>(null)

    // calculate score from boardOrientation
    val score = mutableStateOf(activePosition.calculateScore(boardOrientation))


    val turnPlayerPiecesPositions: List<Coordinate>
        get() = activePosition.board.piecesColorPosition(activePlayer).map { it.key }


    val reachableSquares = mutableStateOf<List<Coordinate>?>(null)
    val captureMoveSquares = mutableStateOf<List<Coordinate>?>(null)

    val allLegalDestination: List<Coordinate>
        get() = getAllLegalMoves()

    fun getAllLegalMoves(): List<Coordinate>{
        val allLegalCoordinate = emptyList<Coordinate>().toMutableList()

        activePosition.board.piecesColorPosition(activePlayer).forEach {
                entry ->
            getReachableandCaptureSquares(entry.key).forEach {
                allLegalCoordinate.add(it)
            }
        }

        return allLegalCoordinate
    }

    private fun setReachableSquare(pieceCoordinate: Coordinate){
        val fetchedPairList = getReachableandCaptureSquares(pieceCoordinate)
        reachableSquares.value = fetchedPairList
    }


    private fun getReachableandCaptureSquares(pieceCoordinate: Coordinate) : List<Coordinate> {


        val fetchedPairList = activePosition.board.getSquare(pieceCoordinate).piece!!.reachableSquares(activePosition)

        val calculateReachableSquares = fetchedPairList.filter {
            val potentialPosition = calculateNewPosition(
                activePosition,
                AppliedMove(pieceCoordinate, it, activePosition)
            )

            !potentialPosition.isKingCheck(activePlayer)
        }






        // Castle Menace Validation // TODO - SIMPLIFY
        val listPositionsMenaced = emptyList<Coordinate>().toMutableList()
        val unavailableCastleSquares = emptyList<Coordinate>().toMutableList()

        if (activePosition.board.getSquare(pieceCoordinate).piece!!::class.java == King::class.java) { // TODO SIMPLIFY- DON'T WORK IN Piece.FunMoves??


            activePosition.board.piecesColorPosition(activePlayer.opponent())
                .forEach {  // opponent() because turn as been made
                        entry ->
                    entry.value.reachableSquares(activePosition).forEach {
                        listPositionsMenaced.add(it)
                    }
                }

            if (activePosition.board.getSquare(pieceCoordinate).piece!!::class.java == King::class.java) {

                when (pieceCoordinate) {
                    Coordinate.E1 -> {
                        if (listPositionsMenaced.contains(Coordinate.F1)) {
                            unavailableCastleSquares.add(Coordinate.G1)
                        }
                        if (listPositionsMenaced.contains(Coordinate.D1)) {
                            unavailableCastleSquares.add(Coordinate.C1)
                        }
                    }

                    Coordinate.E8 -> {
                        if (listPositionsMenaced.contains(Coordinate.F8)) {
                            unavailableCastleSquares.add(Coordinate.G8)
                        }
                        if (listPositionsMenaced.contains(Coordinate.D8)) {
                            unavailableCastleSquares.add(Coordinate.C8)
                        }
                    }

                    else -> {}
                }


            }
        }

        return calculateReachableSquares - unavailableCastleSquares.toSet()

    }

    private fun resetReachableSquare(){
        reachableSquares.value = null
        captureMoveSquares.value = null
    }


    val showPromotionDialog = mutableStateOf(false)


    var proposedMove: ProposedMove? = null

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
        if (selectedSquare.value == null) {

            if (turnPlayerPiecesPositions.contains(square.coordinate)) {
                selectedSquare.value = square.coordinate
                setReachableSquare(selectedSquare.value!!)
            }

        } else {
            if (square.coordinate == selectedSquare.value) {
                selectedSquare.value = null
                resetReachableSquare()
            } else {
                if (reachableSquares.value!!.contains(square.coordinate)) {
                    destinationSquare.value = square.coordinate

                    proposedMove = ProposedMove(selectedSquare.value!!, destinationSquare.value!!, activePosition)

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

            JetpackChessMode.PUZZLE ->
                if (proposedMove.from == activePosition.nextMove!!.from && proposedMove.to == activePosition.nextMove!!.to && proposedMove.promotionTo == activePosition.nextMove!!.promotionTo){
                    resetWrongMoveDecorator()
                    applyAutoMove()
                } else {
                    resetSelectedSquare()
                    setWrongMoveDecorator(proposedMove)
                    statusMistake()
                }
            JetpackChessMode.SCROLL -> {}

        }

    }

    private fun updateCheckmateAndStalemateStatus(){
        val calculateLegalDestination = allLegalDestination
        //activePosition.isActivePlayerKingInCheckmate = calculateLegalDestination.isEmpty() && isKingCheck
        //activePosition.isActivePlayerKingInStalemate = calculateLegalDestination.isEmpty() && !isKingCheck
        resetReachableSquare() // TODO USELESS ??
    }

    fun applyMove(
        from: Coordinate,
        to: Coordinate,
        typePiecePromoteTo: Class<out Piece>? = null
    ) {
        val move = AppliedMove(from, to, activePosition, typePiecePromoteTo)
        val newPosition = calculateNewPosition(activePosition, move)
        addMoveMadeGamePosition(move)
        addGamePositionInTimeline(newPosition)
        updateButtonState()
    }

    fun applyAutoMove() {
        forwardActivePosition()

        if (activePositionIndex < gameTimeline.positionsTimeline.lastIndex) // Stop if in last position
            viewModelScope.launch(Dispatchers.IO) {
                delay(500L) // delay before auto-applying the next move
                forwardActivePosition()
            }
    }


    fun addMoveMadeGamePosition(moveUCI: AppliedMove) {
        gameTimeline.positionsTimeline[gameTimeline.positionsTimeline.lastIndex].nextMove = moveUCI // TODO - SIMPLIFY ?
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

}



enum class STATUS{
    PENDING,
    SCROLLING,
    IN_PROGRESS_GAME,
    IN_PROGRESS_OK,
    IN_PROGRESS_WRONG,
    FINISH_CHECKMATE,
    FINISH_STALEMATE,
    FINISH_OK,
    FINISH_WRONG,
}




