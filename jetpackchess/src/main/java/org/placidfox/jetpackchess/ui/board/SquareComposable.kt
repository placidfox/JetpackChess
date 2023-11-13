package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.viewModel.UIViewModel


@Composable
fun SquareComposable(uiState: UIViewModel, square: Square){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = if (square.isLight) {
                    uiState.boardColorState.value.lightSquareColor
                } else {
                    uiState.boardColorState.value.darkSquareColor
                }
            )
            .clickable { uiState.clickedSquare(square) },
        contentAlignment = Alignment.Center


    ){
        DecoratorSelected(uiState, square)
        DecoratorDestination(uiState, square)
        DecoratorPreviousMoves(uiState, square)
        DecoratorWrongMove(uiState, square)
        Coordinate(uiState, square)
        PieceComposable(uiState, square)

    }

}

