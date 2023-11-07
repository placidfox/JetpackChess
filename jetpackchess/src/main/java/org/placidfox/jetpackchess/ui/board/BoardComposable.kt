package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.placidfox.jetpackchess.model.piece.PlayerColor.*
import org.placidfox.jetpackchess.viewModel.UIViewModel

@Composable
fun BoardComposable (uiState: UIViewModel){

    when(uiState.boardOrientationState.value) {

        WHITE ->
            Box(Modifier.fillMaxWidth().aspectRatio(1f)) {
                Row {
                    for (file in 1..8) {
                        Column (modifier = Modifier.weight(1f)) {
                            for (rank in 8 downTo 1) {
                                //SquareComposable(uiState, uiState.gameTimeline.positionsTimeline[uiState.activePositionIndex.value].board.getSquare(file, rank))
                                SquareComposable(uiState, uiState.activePosition.value.board.getSquare(file, rank))
                            }
                        }

                    }
                }
            }

        BLACK ->
            Box (Modifier.fillMaxWidth().aspectRatio(1f)) {
                Row {
                    for (file in 8 downTo 1) {
                        Column(modifier = Modifier.weight(1f)) {
                            for (rank in 1..8) {
                                SquareComposable(uiState, uiState.activePosition.value.board.getSquare(file, rank))
                            }
                        }

                    }
                }
            }

    }


}


enum class BoardColor (val lightSquareColor: Color, val darkSquareColor: Color){
    Wood(Color(red = 255, green = 255, blue = 224), Color(red = 160, green = 82, blue = 45)),
    Blue(Color(red = 225 , green = 235, blue = 238), Color(red = 114, green = 160, blue = 193)),
}

enum class DecoratorColor (val color: Color){
    SELECTED_SQUARE(Color.Blue),
    PREVIOUS_MOVE_COLOR(Color.Yellow),
    CHECK_COLOR(Color.Red)

}

