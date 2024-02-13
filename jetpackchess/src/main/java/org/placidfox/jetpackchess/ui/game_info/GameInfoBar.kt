package org.placidfox.jetpackchess.ui.game_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.placidfox.jetpackchess.model.piece.PlayerColor

import org.placidfox.jetpackchess.viewModel.GameViewModel


@Composable
fun GameInfoBar(viewModel: GameViewModel){
    Row (modifier = Modifier
        .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,

    ){
        PiecesInfo(viewModel.uiState.boardOrientation, viewModel)
        ScoreInfo(viewModel)
    }

}



// TODO () Pieces captured White in top, Black on Bottom, ...
@Composable
fun PiecesInfo(color: PlayerColor ,viewModel: GameViewModel){
    viewModel.activePosition.capturedPieces.forEach {
        if(it.color != color) {
            Text(it.imgSymbol)
        }
    }

}

@Composable
fun ScoreInfo(viewModel: GameViewModel){
        Text(viewModel.uiState.score.toString())

}