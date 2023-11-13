package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.viewModel.UIViewModel


@Composable
fun DecoratorSelected(uiState: UIViewModel, square: Square){

    if (square.coordinate == uiState.selectedSquare.value){
        Box(modifier = Modifier.fillMaxSize().drawBehind {
                drawRect(
                    color = DecoratorColor.SELECTED_SQUARE.color,
                    alpha = if (square.isLight) {0.7f}else{0.7f}
                )
            }
        )
    }

}

@Composable
fun DecoratorDestination(uiState: UIViewModel, square: Square){

    if (square.coordinate == uiState.destinationSquare.value){
        Box(modifier = Modifier.fillMaxSize().drawBehind {
            drawRect(
                color = DecoratorColor.SELECTED_SQUARE.color,
                alpha = if (square.isLight) {0.9f}else{0.9f}
            )
        }
        )
    }

}

@Composable
fun DecoratorPreviousMoves(uiState: UIViewModel, square: Square){

    if (uiState.lastMovePosition?.contains(square.coordinate) == true){
        Box(modifier = Modifier.fillMaxSize().drawBehind {
                drawRect(
                    color = DecoratorColor.PREVIOUS_MOVE_COLOR.color,
                    alpha = if (square.isLight) {0.6f}else{0.75f}
                )
            }
        )
    }
}

@Composable
fun DecoratorWrongMove(uiState: UIViewModel, square: Square){

    if (uiState.wrongMovePosition.value?.contains(square.coordinate) == true){
        Box(modifier = Modifier.fillMaxSize().drawBehind {
            drawRect(
                color = DecoratorColor.WRONG_MOVE_COLOR.color,
                alpha = if (square.isLight) {0.8f}else{0.75f}
            )
        }
        )
    }
}




enum class DecoratorColor (val color: Color){
    SELECTED_SQUARE(Color.Blue),
    PREVIOUS_MOVE_COLOR(Color.Yellow),
    WRONG_MOVE_COLOR(Color.Red),
    CHECK_COLOR(Color.Magenta)
}

