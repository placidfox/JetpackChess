package org.placidfox.jetpackchess.model.game.parameters

import org.placidfox.jetpackchess.model.board.Coordinate


data class EnPassantStatus(
    val isEnPassantPossible: Boolean,
    val enPassantCoordinate: Coordinate?
){


}