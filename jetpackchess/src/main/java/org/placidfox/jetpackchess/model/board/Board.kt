package org.placidfox.jetpackchess.model.board

import org.placidfox.jetpackchess.model.piece.*

data class Board(
    val piecesPosition: Map<Coordinate, Piece>
) {

    val squares = Coordinate.entries.associateWith { coordinate ->
        Square(coordinate, piecesPosition[coordinate])
    }

    fun piecesColorPosition(color: PlayerColor): Map<Coordinate, Piece> =
        piecesPosition.filter { (_, piece) -> piece.color == color }

    fun piecesColorPositionMinusKing(color: PlayerColor): Map<Coordinate, Piece> =
        piecesColorPosition(color).filter { it.value::class.java != King::class.java }

    fun emptySquares(): List<Coordinate> =
        Coordinate.entries.filter { !piecesPosition.keys.contains(it) }


    fun getSquare(coordinate: Coordinate): Square =
        squares[coordinate]!!

    fun getSquare(file: Int, rank: Int): Square =
        squares[Coordinate.fromNumCoordinate(file, rank)]!!

    fun isOccupied(coordinate: Coordinate): Boolean =
        findPiece(coordinate) != null

    fun findPiece(coordinate: Coordinate): Piece? =
        getSquare(coordinate).piece

    fun findSquare(piece: Piece): Square? =
        squares.values.firstOrNull { it.piece == piece }

}



