package org.placidfox.jetpackchess.model.game

import org.placidfox.jetpackchess.controller.JetpackChessMode


data class GameTimeline (
    val mode: JetpackChessMode,
    var positionsTimeline : MutableList<GamePosition>,

    ){


    fun addGamePosition(gamePosition: GamePosition){
        positionsTimeline.add(gamePosition)
    }

    fun initNewTimeline(gamePosition: GamePosition){
        positionsTimeline = mutableListOf(gamePosition)
    }




}



