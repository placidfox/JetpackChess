package org.placidfox.jetpackchess.model.game

import org.placidfox.jetpackchess.controller.JetpackChessMode
import org.placidfox.jetpackchess.model.game.parameters.GameMetadata
import org.placidfox.jetpackchess.model.game.parameters.Metadata
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_NAME
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_SPECIFIC
import org.placidfox.jetpackchess.model.game.parameters.OpeningMetadata
import org.placidfox.jetpackchess.model.game.parameters.PuzzleMetadata

data class GameTimeline (
    val mode: JetpackChessMode,
    var positionsTimeline : MutableList<GamePosition>,
    var gameMetadata: Metadata =
        when(mode){
            JetpackChessMode.GAME -> GameMetadata.initializeMetadata()
            JetpackChessMode.PUZZLE -> PuzzleMetadata.initializeMetadata()
            JetpackChessMode.OPENING_TEST -> OpeningMetadata.initializeMetadata()
            JetpackChessMode.OPENING_SCROLL -> OpeningMetadata.initializeMetadata()
        },

    ){


    fun addGamePosition(gamePosition: GamePosition){
        positionsTimeline.add(gamePosition)
    }

    fun initNewTimeline(gamePosition: GamePosition){
        positionsTimeline = mutableListOf(gamePosition)
    }

    fun setOpeningMetadata(openingName: String, openingSpecific: String){
        gameMetadata = OpeningMetadata(
            mapOf(
                OPENING_NAME to openingName,
                OPENING_SPECIFIC to openingSpecific,
                )
        )

    }



    fun importMetadata(){
        TODO()
    }


}



