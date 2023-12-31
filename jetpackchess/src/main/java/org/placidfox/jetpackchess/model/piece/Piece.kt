package org.placidfox.jetpackchess.model.piece
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition


interface Piece {

    val color: PlayerColor

    val asset: Int
    val assetRatio: Float

    val imgSymbol: String
    val textSymbol: String

    val FENSymbol: String

    val value: Int

    fun reachableSquares(position: GamePosition): List<Coordinate>  // List of square where the piece can move (without check validation)

}




enum class PlayerColor {
    WHITE,
    BLACK;

    fun opponent(): PlayerColor {
        return when(this){
            WHITE -> BLACK
            BLACK -> WHITE
        }
    }
}

