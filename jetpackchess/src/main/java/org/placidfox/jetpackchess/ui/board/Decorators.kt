package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.game.Termination
import org.placidfox.jetpackchess.viewModel.GameViewModel


@Composable
fun DecoratorSelected(viewModel: GameViewModel, square: Square){

    if (viewModel.uiState.selectedSquare.contains(square.coordinate)){
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
fun DecoratorKingCheck(viewModel: GameViewModel, square: Square){

    if(square.hasColorKing(viewModel.activePosition.activePlayer) && viewModel.activePosition.isActiveKingCheck){
        Box(modifier = Modifier
            .fillMaxSize(0.91f)
            .drawBehind {
                drawRect(
                    color = if (viewModel.activePosition.termination == Termination.CHECKMATE) {
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
fun DecoratorKingStalemate(viewModel: GameViewModel, square: Square){

    if(square.hasColorKing(viewModel.activePosition.activePlayer) && viewModel.activePosition.termination == Termination.STALEMATE){
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
fun DecoratorPossibleDestination(viewModel: GameViewModel, square: Square){ // TODO : TO REFACTOR

    if (viewModel.uiState.moveSquares.contains(square.coordinate) && (square.isNotEmpty || square.coordinate == viewModel.activePosition.enPassantStatus.enPassantCoordinate)){
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
        if (viewModel.uiState.moveSquares.contains(square.coordinate)){
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
fun DecoratorPreviousMoves(viewModel: GameViewModel, square: Square){

    if (viewModel.activePosition.lastMovePositions?.contains(square.coordinate) == true){
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
fun DecoratorWrongMove(viewModel: GameViewModel, square: Square){

    if (viewModel.uiState.wrongChoiceSquares.contains(square.coordinate)){
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    color = DecoratorColor.WRONG_MOVE_COLOR.color,
                    alpha = if (square.isLight) {
                        0.7f
                    } else {
                        0.8f
                    }
                )
            }
        )
    }
}

@Composable
fun DecoratorHint(viewModel: GameViewModel, square: Square){

    if (viewModel.uiState.hintSquare.contains(square.coordinate)){
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    color = DecoratorColor.HINT_COLOR.color,
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
    HINT_COLOR(Color(red = 236, green = 159, blue = 83)),
    POSSIBLE_DESTINATION_COLOR(Color.DarkGray),
    CHECK_COLOR(Color.Red),
    CHECKMATE_COLOR(Color.Green),
    STALEMATE_COLOR(Color(red = 255, green = 195, blue = 11)),
}

