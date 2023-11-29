package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.viewModel.GameViewModel


@Composable
fun SquareComposable(viewModel: GameViewModel, square: Square){
    Box(
        modifier = Modifier
            .testTag(square.coordinate.textName) // TO DELETE ?
            .aspectRatio(1f)
            .background(
                color = if (square.isLight) {
                    viewModel.uiState.boardColor.lightSquareColor
                } else {
                    viewModel.uiState.boardColor.darkSquareColor
                }
            )
            .clickable {viewModel.clickSquare(square)},
        contentAlignment = Alignment.Center


    ){

        DecoratorPreviousMoves(viewModel, square)
        DecoratorWrongMove(viewModel, square)
        DecoratorSelected(viewModel, square)
        DecoratorKingCheck(viewModel, square)
        DecoratorKingStalemate(viewModel, square)
        Coordinate(viewModel, square)
        PieceComposable(viewModel, square)
        DecoratorPossibleDestination(viewModel, square) // to be in front of the piece asset


    }

}

