package org.placidfox.jetpackchess.model.game.parameters


enum class CastleType{
    WHITE_SHORT_CASTLE,
    WHITE_LONG_CASTLE,
    BLACK_SHORT_CASTLE,
    BLACK_LONG_CASTLE
}

data class CastlingStatus(
    var whiteShortCastlePossible: Boolean,
    var whiteLongCastlePossible: Boolean,
    var blackShortCastlePossible: Boolean,
    var blackLongCastlePossible: Boolean,
){

    fun whiteKingAsMoved (){
        whiteShortCastlePossible = false
        whiteLongCastlePossible = false
    }

    fun blackKingAsMoved (){
        blackShortCastlePossible = false
        blackLongCastlePossible = false
    }

    fun whiteRookH1asMoves(){
        whiteShortCastlePossible = false
    }

    fun whiteRookA1asMoves(){
        whiteLongCastlePossible = false
    }

    fun blackRookH8asMoves(){
        blackShortCastlePossible = false
    }

    fun blackRookA8asMoves(){
        blackLongCastlePossible = false
    }


}