package org.placidfox.jetpackchess.model.game

import org.placidfox.jetpackchess.controller.JetpackChessMode



data class GameTimeline (
    val mode: JetpackChessMode,
    var positionsTimeline : MutableList<GamePosition>,
    val status: STATUS = STATUS.IN_PROGRESS_GAME
    // TODO Metadata,
    ){

    var activePositionIndex: Int = 0
    var lastSeenPosition: Int = 0

    val isActivePositionFirst: Boolean
        get() = activePositionIndex == 0

    val isActivePositionLast: Boolean
        get() = when(mode) {
            JetpackChessMode.GAME, JetpackChessMode.SCROLL -> activePositionIndex == positionsTimeline.lastIndex
            JetpackChessMode.PUZZLE -> activePositionIndex == lastSeenPosition // TODO - TO TEST
        }

    val lastPosition: GamePosition
        get() = positionsTimeline.last()


    fun addGamePosition(gamePosition: GamePosition){
        positionsTimeline.add(gamePosition)
        lastPosition.calculateTermination() // TODO BEST POSITION FOR CHECKMATE CHECK ?
    }

    fun initNewTimeline(gamePosition: GamePosition){
        positionsTimeline = mutableListOf(gamePosition)
        activePositionIndex = 0
        lastSeenPosition = 0
    }

    fun initStartPosition(index: Int){
        activePositionIndex = index
        lastSeenPosition = index
    }

    fun changeActivePosition(index: Int) {
        activePositionIndex = index
    }

    fun forwardActivePosition(){
        changeActivePosition (activePositionIndex + 1)
        updateMaxSeenPosition() // Only if forward 1 move
    }

    fun backwardActivePosition(){
        changeActivePosition (activePositionIndex - 1)
    }

    private fun updateMaxSeenPosition() {
        if (activePositionIndex > lastSeenPosition) {
            lastSeenPosition = activePositionIndex
        }
    }


    fun updateStatus(){ // TODO TO SIMPLIFY

        when(mode){
            JetpackChessMode.GAME -> {
                if (lastPosition.termination == Termination.CHECKMATE) {
                    STATUS.FINISH_CHECKMATE
                }
                if (lastPosition.termination == Termination.STALEMATE) {
                    STATUS.FINISH_STALEMATE
                }
            }
            JetpackChessMode.PUZZLE -> TODO()
            JetpackChessMode.SCROLL -> TODO()
        }

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