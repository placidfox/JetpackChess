package org.placidfox.jetpackchess.model.board

enum class Coordinate {
    A1, A2, A3, A4, A5, A6, A7, A8,
    B1, B2, B3, B4, B5, B6, B7, B8,
    C1, C2, C3, C4, C5, C6, C7, C8,
    D1, D2, D3, D4, D5, D6, D7, D8,
    E1, E2, E3, E4, E5, E6, E7, E8,
    F1, F2, F3, F4, F5, F6, F7, F8,
    G1, G2, G3, G4, G5, G6, G7, G8,
    H1, H2, H3, H4, H5, H6, H7, H8;

    val file: Int = ordinal / 8 + 1 // file a = 1 , h = 8

    val fileLetter: Char =
        toString().lowercase()[0]

    val rank: Int = ordinal % 8 + 1 // a1 = 1, d4 = 4, ...

    val textName: String = "$fileLetter$rank"

    val coordinateInt: Int = "$file$rank".toInt()

    companion object {

        fun Coordinate.isLightSquare(): Boolean =
            (ordinal + file % 2) % 2 == 0

        fun Coordinate.isDarkSquare(): Boolean =
            (ordinal + file % 2) % 2 == 1

        fun Coordinate.toNum(): Int =
            "$file$rank".toInt()

        fun fromNumCoordinate(file: Int, rank: Int): Coordinate {

            return entries[(file - 1) * 8 + (rank - 1)]
        }


        fun fromString(coordinate: String): Coordinate {
            return entries.first() { it.textName == coordinate }
        }

        fun getCoordinateRight(coordinate: Coordinate): Coordinate? =
            if(coordinate.file < 8 ){
                Coordinate.fromNumCoordinate(coordinate.file + 1, coordinate.rank)
            } else {
                null
            }

        fun getCoordinateLeft(coordinate: Coordinate): Coordinate? =
            if(coordinate.file > 1 ){
                Coordinate.fromNumCoordinate(coordinate.file - 1, coordinate.rank)
            } else {
                null
            }



    }
}


val boardCoordinateNum = listOf( // to check if a destionationIsOnTheBoard
    18, 28, 38, 48, 58, 68, 78, 88,
    17, 27, 37, 47, 57, 67, 77, 87,
    16, 26, 36, 46, 56, 66, 76, 86,
    15, 25, 35, 45, 55, 65, 75, 85,
    14, 24, 34, 44, 54, 64, 74, 84,
    13, 23, 33, 43, 53, 63, 73, 83,
    12, 22, 32, 42, 52, 62, 72, 82,
    11, 21, 31, 41, 51, 61, 71, 81
)
