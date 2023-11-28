package org.placidfox.jetpackchess.viewModel


import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.ui.board.BoardColor

data class UIState(
    var activePosition: GamePosition,

    val isActivePositionFirst: Boolean,
    val isActivePositionLast: Boolean,

    val status: STATUS,

    val activePlayer: PlayerColor,

    var boardOrientation: PlayerColor,
    var boardColor: BoardColor,

    var selectedSquare: Coordinate?,
    var wrongMoveSquares: List<Coordinate>,
    //val hintSquares: List<Coordinate>> // TODO ADD FEATURE

    var moveDestinationSquares: List<Coordinate>,

    val showPromotionDialog: Boolean,
    )



