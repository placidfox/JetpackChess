package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.viewModel.UIViewModel


@Composable
fun PieceComposable(uiState: UIViewModel, square: Square){

    if (square.isNotEmpty) {
        Image(
            painter = painterResource(id = square.piece!!.asset),
            contentDescription = "pieces",
            modifier = Modifier.fillMaxSize(square.piece.assetRatio)
        )
    }

}