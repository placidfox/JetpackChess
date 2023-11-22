package org.placidfox.jetpackchess.model.board


import org.placidfox.jetpackchess.model.board.Coordinate.Companion.isDarkSquare
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.isLightSquare
import org.placidfox.jetpackchess.model.piece.King
import org.placidfox.jetpackchess.model.piece.Piece
import org.placidfox.jetpackchess.model.piece.PlayerColor


data class Square(
    val coordinate: Coordinate,
    val piece: Piece? = null
) {

    val file: Int =
        coordinate.file

    val rank: Int =
        coordinate.rank

    val coordinateText: String =
        coordinate.textName

    val isLight: Boolean =
        coordinate.isLightSquare()

    val isDark: Boolean =
        coordinate.isDarkSquare()

    val isEmpty: Boolean
        get() = piece == null

    val isNotEmpty: Boolean
        get() = !isEmpty


    val hasWhitePiece: Boolean
        get() = piece?.color == PlayerColor.WHITE

    val hasBlackPiece: Boolean
        get() = piece?.color == PlayerColor.BLACK


    fun hasColorKing(color: PlayerColor): Boolean =
        piece?.color == color && piece::class.java == King::class.java


    fun hasColorPiece(color: PlayerColor): Boolean =
        piece?.color == color



}
