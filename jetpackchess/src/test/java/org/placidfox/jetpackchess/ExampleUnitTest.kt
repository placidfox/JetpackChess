package org.placidfox.jetpackchess

import org.junit.Test

import org.junit.Assert.*
import org.placidfox.jetpackchess.controller.*
import org.placidfox.jetpackchess.model.board.Board
import org.placidfox.jetpackchess.model.board.Coordinate
import org.placidfox.jetpackchess.model.game.GamePosition
import org.placidfox.jetpackchess.model.piece.PlayerColor

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}


class TestPositionState {

    val splitFen = FEN_DEFAULT_POSITION.split(" ")

    val position = GamePosition(
        Board(splitFenPosition(splitFen[0])),
        splitPlayerTurn(splitFen[1]),
        splitCastlingPossibilities(splitFen[2]),
        splitEnPassant(splitFen[3]),
        splitHalfMove(splitFen[4]),
        splitMoveNumber(splitFen[5])
    )

    val A2InitialDestination = listOf(Coordinate.A4, Coordinate.A3)

    @Test
    fun testReachablePawnA2(){

        assert(position.pieceLegalDestinations(Coordinate.A2).isNotEmpty())
        assert(position.pieceLegalDestinations(Coordinate.A2) == A2InitialDestination)

        assert(!position.isActiveKingCheck)

    }

    val testFEN = "4k3/4r3/8/8/8/8/4Q3/4K3 b - - 0 1"

    val splitFenPinned = testFEN.split(" ")

    val positionPinned = GamePosition(
        Board(splitFenPosition(splitFenPinned[0])),
        splitPlayerTurn(splitFenPinned[1]),
        splitCastlingPossibilities(splitFenPinned[2]),
        splitEnPassant(splitFenPinned[3]),
        splitHalfMove(splitFenPinned[4]),
        splitMoveNumber(splitFenPinned[5])
    )

    @Test
    fun testPinned(){

        assert(positionPinned.activePlayer == PlayerColor.BLACK)
        assert(!positionPinned.isActiveKingCheck)

        assert(positionPinned.pieceLegalDestinations(Coordinate.E8).isNotEmpty())

        println(positionPinned.pieceLegalDestinations(Coordinate.E8))
        println(positionPinned.pieceLegalDestinations(Coordinate.E7))

        assert(positionPinned.isLegalMoves())

    }

    val testFENMate = "4k3/4Q3/4K3/8/8/8/8/8 b - - 0 1"

    val splitFenMate = testFENMate.split(" ")

    val positionCheckmate = GamePosition(
        Board(splitFenPosition(splitFenMate[0])),
        splitPlayerTurn(splitFenMate[1]),
        splitCastlingPossibilities(splitFenMate[2]),
        splitEnPassant(splitFenMate[3]),
        splitHalfMove(splitFenMate[4]),
        splitMoveNumber(splitFenMate[5])
    )

    @Test
    fun testMated(){

        assert(positionCheckmate.isActiveKingCheck)

        assert(!positionCheckmate.isLegalMoves())

        //assert(positionCheckmate.isCheckmate)

    }

    val testStaleMate = "k7/8/1Q6/8/8/8/8/K7 b - - 0 1"

    val splitFenStaleMate = testStaleMate.split(" ")

    val positionStalemate = GamePosition(
        Board(splitFenPosition(splitFenStaleMate[0])),
        splitPlayerTurn(splitFenStaleMate[1]),
        splitCastlingPossibilities(splitFenStaleMate[2]),
        splitEnPassant(splitFenStaleMate[3]),
        splitHalfMove(splitFenStaleMate[4]),
        splitMoveNumber(splitFenStaleMate[5])
    )

    @Test
    fun testStaleMated(){

        assert(!positionStalemate.isActiveKingCheck)

        assert(!positionStalemate.isLegalMoves())

        //assert(positionStalemate.isStalemate)

    }



    val testFENCastleMenaced = "rn1qkbnr/p1pp1ppp/bp6/4p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 0 4"

    val splitFenCastleMenaced = testFENCastleMenaced.split(" ")

    val positionCastleMenaced = GamePosition(
        Board(splitFenPosition(splitFenCastleMenaced[0])),
        splitPlayerTurn(splitFenCastleMenaced[1]),
        splitCastlingPossibilities(splitFenCastleMenaced[2]),
        splitEnPassant(splitFenCastleMenaced[3]),
        splitHalfMove(splitFenCastleMenaced[4]),
        splitMoveNumber(splitFenCastleMenaced[5])
    )

    @Test
    fun testCastleMenaced(){

        println(positionCastleMenaced.pieceLegalDestinations(Coordinate.E1))

        assert(positionCastleMenaced.pieceLegalDestinations(Coordinate.E1).isEmpty())



    }


    val testFENLongBKCastleMenaced = "r3k1nr/pb1p1ppp/n1p5/qp2p1B1/P3P3/b1PP4/1P3PPP/RN1QKBNR b KQkq - 4 8"

    val splitFenBKLongCastleMenaced = testFENLongBKCastleMenaced.split(" ")

    val positionLongBKCastleMenaced = GamePosition(
        Board(splitFenPosition(splitFenBKLongCastleMenaced[0])),
        splitPlayerTurn(splitFenBKLongCastleMenaced[1]),
        splitCastlingPossibilities(splitFenBKLongCastleMenaced[2]),
        splitEnPassant(splitFenBKLongCastleMenaced[3]),
        splitHalfMove(splitFenBKLongCastleMenaced[4]),
        splitMoveNumber(splitFenBKLongCastleMenaced[5])
    )

    @Test
    fun testLongCastleBlackMenaced(){

        println(positionLongBKCastleMenaced.pieceLegalDestinations(Coordinate.E8))

        assert(!positionLongBKCastleMenaced.pieceLegalDestinations(Coordinate.E8).contains(Coordinate.C8))



    }


}