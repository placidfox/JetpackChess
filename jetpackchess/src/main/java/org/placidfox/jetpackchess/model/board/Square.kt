package org.placidfox.jetpackchess.model.board


import org.placidfox.jetpackchess.model.board.Coordinate.Companion.isDarkSquare
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.isLightSquare
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor


data class Square(
    val position: Coordinate,
    val piece: Piece? = null
) {

    val file: Int =
        position.file

    val rank: Int =
        position.rank

    val coordinateText: String =
        position.textName

    val isLight: Boolean =
        position.isLightSquare()

    val isDark: Boolean =
        position.isDarkSquare()

    val isEmpty: Boolean
        get() = piece == null

    val isNotEmpty: Boolean
        get() = !isEmpty


    val hasWhitePiece: Boolean
        get() = piece?.color == PlayerColor.WHITE

    val hasBlackPiece: Boolean
        get() = piece?.color == PlayerColor.BLACK


    fun hasColorPiece(color: PlayerColor): Boolean =
        piece?.color == color



}
