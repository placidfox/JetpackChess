package org.placidfox.jetpackchess.ui.control

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.placidfox.jetpackchess.model.piece.*
import org.placidfox.jetpackchess.viewModel.GameViewModel
import org.placidfox.jetpackchess.viewModel.cancelPromotion
import org.placidfox.jetpackchess.viewModel.promotionChoice


@Composable
fun PromotionDialog(
    viewModel: GameViewModel,
){
    Dialog(
        onDismissRequest = {viewModel.cancelPromotion()},
        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row (modifier = Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterVertically){
                PieceIcon(Queen::class.java, viewModel)
                PieceIcon(Rook::class.java, viewModel)
                PieceIcon(Bishop::class.java, viewModel)
                PieceIcon(Knight::class.java, viewModel)
            }
        }
    }

}

@Composable
fun PieceIcon(pieceType: Class<out Piece>, viewModel: GameViewModel){

    val pieceIcon: Int = when(pieceType){
        Queen::class.java -> Queen(viewModel.uiState.activePosition.activePlayer).asset
        Rook::class.java -> Rook(viewModel.uiState.activePosition.activePlayer).asset
        Bishop::class.java -> Bishop(viewModel.uiState.activePosition.activePlayer).asset
        Knight::class.java -> Knight(viewModel.uiState.activePosition.activePlayer).asset
        else -> 0
    }

    Image(
        painter = painterResource(id = pieceIcon),
        contentDescription = "piece",
        modifier = Modifier

            .fillMaxHeight(0.3f)
            .clickable {
                viewModel.promotionChoice(pieceType)
            }

    )
}