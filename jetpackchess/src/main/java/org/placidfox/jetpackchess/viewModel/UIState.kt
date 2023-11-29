package org.placidfox.jetpackchess.viewModel


import org.placidfox.jetpackchess.controller.initialGamePosition
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.board.BoardColor


data class UIState (

    val activePosition: GamePosition = initialGamePosition,

    val boardOrientation: PlayerColor = PlayerColor.WHITE,
    val boardColor: BoardColor = BoardColor.Blue,

    var selectedSquare: List<Coordinate> = emptyList(),
    val moveSquares: List<Coordinate> = emptyList(),
    val wrongChoiceSquares: List<Coordinate> = emptyList(),

    val showPromotionDialog: Boolean = false,

    val isActivePositionFirst: Boolean = true,
    val isActivePositionLast: Boolean = true,

    )