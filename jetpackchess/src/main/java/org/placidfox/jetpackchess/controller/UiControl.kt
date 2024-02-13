package org.placidfox.jetpackchess.controller

import org.placidfox.jetpackchess.ui.board.BoardColor


fun Controller.setBoardColor(color: BoardColor){
    viewModel.uiState.boardColor = color
}