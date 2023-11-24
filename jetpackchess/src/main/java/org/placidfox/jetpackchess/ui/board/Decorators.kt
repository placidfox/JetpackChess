package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.viewModel.UIViewModel


@Composable
fun DecoratorSelected(uiState: UIViewModel, square: Square){

    if (square.coordinate == uiState.selectedSquare.value){
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    color = DecoratorColor.SELECTED_SQUARE.color,
                    alpha = if (square.isLight) {
                        0.7f
                    } else {
                        0.7f
                    }
                )
            }
        )
    }

}

@Composable
fun DecoratorKingCheck(uiState: UIViewModel, square: Square){

    if(square.hasColorKing(uiState.activePlayer.value) && uiState.isKingCheck){
        Box(modifier = Modifier
            .fillMaxSize(0.91f)
            .drawBehind {
                drawRect(
                    color = if (uiState.isKingCheckmate) {
                        DecoratorColor.CHECKMATE_COLOR.color
                    } else {
                        DecoratorColor.CHECK_COLOR.color
                    },
                    style = Stroke(width = size.width / 10)
                )
            })
    }
}

@Composable
fun DecoratorKingStalemate(uiState: UIViewModel, square: Square){

    if(square.hasColorKing(uiState.activePlayer.value) && uiState.isKingStalemate){
        Box(modifier = Modifier
            .fillMaxSize(0.91f)
            .drawBehind {
                drawRect(
                    color = DecoratorColor.STALEMATE_COLOR.color,
                    style = Stroke(width = size.width / 10)
                )
            })
    }
}


@Composable
fun DecoratorPossibleDestination(uiState: UIViewModel, square: Square){ // TODO : TO REFACTOR

    if (uiState.reachableSquares.value?.contains(square.coordinate) == true && (square.isNotEmpty || square.coordinate == uiState.activePosition.value.enPassantStatus.enPassantCoordinate)){
        Box(modifier = Modifier
            .fillMaxSize(0.7f)
            .drawBehind {
                drawArc(
                    color = DecoratorColor.POSSIBLE_DESTINATION_COLOR.color,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = true,
                    style = Stroke(width = size.width / 8),
                    alpha = if (square.isLight) {
                        0.8f
                    } else {
                        0.75f
                    }
                )
            })
    } else {
        if (uiState.reachableSquares.value?.contains(square.coordinate) == true){
           Box(modifier = Modifier
               .fillMaxSize(0.25f)
               .drawBehind {
                   drawCircle(
                       color = DecoratorColor.POSSIBLE_DESTINATION_COLOR.color,
                       alpha = if (square.isLight) {
                           0.6f
                       } else {
                           0.75f
                       }
                   )
               })
        }
    }
}

@Composable
fun DecoratorPreviousMoves(uiState: UIViewModel, square: Square){

    if (uiState.lastMovePosition?.contains(square.coordinate) == true){
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    color = DecoratorColor.PREVIOUS_MOVE_COLOR.color,
                    alpha = if (square.isLight) {
                        0.6f
                    } else {
                        0.75f
                    }
                )
            }
        )
    }
}

@Composable
fun DecoratorWrongMove(uiState: UIViewModel, square: Square){

    if (uiState.wrongMovePosition.value?.contains(square.coordinate) == true){
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    color = DecoratorColor.WRONG_MOVE_COLOR.color,
                    alpha = if (square.isLight) {
                        0.8f
                    } else {
                        0.75f
                    }
                )
            }
        )
    }
}




enum class DecoratorColor (val color: Color){
    SELECTED_SQUARE(Color.Blue),
    PREVIOUS_MOVE_COLOR(Color.Yellow),
    WRONG_MOVE_COLOR(Color.Red),
    POSSIBLE_DESTINATION_COLOR(Color.DarkGray),
    CHECK_COLOR(Color.Red),
    CHECKMATE_COLOR(Color.Green),
    STALEMATE_COLOR(Color(red = 255, green = 195, blue = 11)),
}

