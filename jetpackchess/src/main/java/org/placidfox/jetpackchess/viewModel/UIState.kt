package org.placidfox.jetpackchess.viewModel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.placidfox.jetpackchess.controller.initialGamePosition
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.game.STATUS
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.board.BoardColor


class UIState {

    var boardOrientation by mutableStateOf(PlayerColor.WHITE)
    var boardColor by mutableStateOf(BoardColor.Blue)

    var score by mutableStateOf(0)
    
    var selectedSquare by mutableStateOf(emptyList<Coordinate>())

    var moveSquares by mutableStateOf(emptyList<Coordinate>())
    var wrongChoiceSquares by mutableStateOf(emptyList<Coordinate>())

    var hintSquare by mutableStateOf(emptyList<Coordinate>())
    var hintSquareButtonActive by mutableStateOf(false)

    var showPromotionDialog by mutableStateOf(false)

    var isActivePositionFirst by mutableStateOf(true)
    var isActivePositionLast by mutableStateOf(true)

    var status by mutableStateOf(STATUS.PENDING) // ONLY USAGE IS IN EXTERNAL APP ?
    
}