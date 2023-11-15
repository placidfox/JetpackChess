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
import org.placidfox.jetpackchess.viewModel.UIViewModel
import org.placidfox.jetpackchess.viewModel.cancelPromotion
import org.placidfox.jetpackchess.viewModel.promotionChoice


@Composable
fun PromotionDialog(
    uiState: UIViewModel,
){
    Dialog(
        onDismissRequest = {uiState.cancelPromotion()},
        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row (modifier = Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterVertically){
                PieceIcon(Queen::class.java, uiState)
                PieceIcon(Rook::class.java, uiState)
                PieceIcon(Bishop::class.java, uiState)
                PieceIcon(Knight::class.java, uiState)
            }
        }
    }

}

@Composable
fun PieceIcon(pieceType: Class<out Piece>, uiState: UIViewModel){

    val pieceIcon: Int = when(pieceType){
        Queen::class.java -> Queen(uiState.activePlayer.value).asset
        Rook::class.java -> Rook(uiState.activePlayer.value).asset
        Bishop::class.java -> Bishop(uiState.activePlayer.value).asset
        Knight::class.java -> Knight(uiState.activePlayer.value).asset
        else -> 0
    }

    Image(
        painter = painterResource(id = pieceIcon),
        contentDescription = "piece",
        modifier = Modifier

            .fillMaxHeight(0.3f)
            .clickable {
                uiState.promotionChoice(pieceType)
            }

    )
}