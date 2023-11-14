package org.placidfox.jetpackchess.controller

import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.parameters.CastlingStatus
import org.placidfox.jetpackchess.model.game.parameters.EnPassantStatus
import org.placidfox.jetpackchess.model.piece.*


fun splitPlayerTurn(fenHalfMove: String) : PlayerColor{

    return when (fenHalfMove){
        "w" -> PlayerColor.WHITE
        else -> PlayerColor.BLACK
    }

}

fun splitCastlingPossibilities(castlingPossibilities: String): CastlingStatus{

    val splitCastlingList = castlingPossibilities.split("")

    var whiteShortCastling = false
    var whiteLongCastling = false
    var blackShortCastling = false
    var blackLongCastling = false


    for (value in splitCastlingList){
        when(value) {
            "K" -> whiteShortCastling = true
            "Q" -> whiteLongCastling = true
            "k" -> blackShortCastling = true
            "q" -> blackLongCastling = true
        }
    }

    return CastlingStatus(whiteShortCastling, whiteLongCastling, blackShortCastling, blackLongCastling)

}

fun splitEnPassant(fenEnPassant: String): EnPassantStatus{
    if (fenEnPassant == "-"){
        return EnPassantStatus(false, null)
    } else {
        return EnPassantStatus(true, Coordinate.fromString(fenEnPassant))
    }

}


fun splitHalfMove(fenHalfMove: String) : Int{
    return fenHalfMove.toInt()
}

fun splitMoveNumber(fenMoveNumber: String) : Int{
    return fenMoveNumber.toInt()
}

fun splitFenPosition(fenPositions: String) : Map<Coordinate, Piece>{

    val mapFenPosition: MutableMap<Coordinate, Piece> = emptyMap<Coordinate, Piece>().toMutableMap()

    val extractFENInitPosition = mutableListOf<String>()
    val matrixFENInitPosition = mutableListOf<List<String>>()

    extractFENInitPosition.add(fenPositions.split("/")[7])
    extractFENInitPosition.add(fenPositions.split("/")[6])
    extractFENInitPosition.add(fenPositions.split("/")[5])
    extractFENInitPosition.add(fenPositions.split("/")[4])
    extractFENInitPosition.add(fenPositions.split("/")[3])
    extractFENInitPosition.add(fenPositions.split("/")[2])
    extractFENInitPosition.add(fenPositions.split("/")[1])
    extractFENInitPosition.add(fenPositions.split("/")[0])

    for (index in 0 until extractFENInitPosition.size) {

        extractFENInitPosition[index] = extractFENInitPosition[index].replace("1", " ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("2", "  ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("3", "   ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("4", "    ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("5", "     ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("6", "      ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("7", "       ")
        extractFENInitPosition[index] = extractFENInitPosition[index].replace("8", "        ")

    }

    for (row in extractFENInitPosition) {
        matrixFENInitPosition.add(row.chunked(1))
    }



    for (rank in 0..7) {
        for (file in 0..7)

            if (matrixFENInitPosition[rank][file] != " "){
                var piece: Piece = Rook(PlayerColor.WHITE) // Possible sans cette fausse déclaration ?

                when (matrixFENInitPosition[rank][file]){
                    "r" -> piece = Rook(PlayerColor.BLACK)
                    "n" -> piece = Knight(PlayerColor.BLACK)
                    "b" -> piece = Bishop(PlayerColor.BLACK)
                    "q" -> piece = Queen(PlayerColor.BLACK)
                    "k" -> piece = King(PlayerColor.BLACK)
                    "p" -> piece = Pawn(PlayerColor.BLACK)

                    "R" -> piece = Rook(PlayerColor.WHITE)
                    "N" -> piece = Knight(PlayerColor.WHITE)
                    "B" -> piece = Bishop(PlayerColor.WHITE)
                    "Q" -> piece = Queen(PlayerColor.WHITE)
                    "K" -> piece = King(PlayerColor.WHITE)
                    "P" -> piece = Pawn(PlayerColor.WHITE)
                }

                mapFenPosition[Coordinate.fromNumCoordinate(file+1, rank+1)] = piece


            }


        }

    return mapFenPosition
}