package org.placidfox.jetpackchess.ui.board

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.viewModel.UIViewModel
import kotlin.math.roundToInt


@Composable
fun PieceComposable(uiState: UIViewModel, square: Square){

    /*
    val xOffset = remember {
        Animatable(0f)
    }

    val yOffset = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit){
        xOffset.animateTo(50f,
            animationSpec = tween(500,
                easing = LinearEasing)
        )
        yOffset.animateTo(
            -50f,
            animationSpec = tween(500,
                easing = LinearEasing)
        )
    }

    */


    var moved by remember { mutableStateOf(false) }
    val pxToMove = with(LocalDensity.current) {
        100.dp.toPx().roundToInt()
    }
    val offset by animateIntOffsetAsState(
        targetValue = if (moved) {
            IntOffset(pxToMove, pxToMove)
        } else {
            IntOffset.Zero
        },
        label = "offset"
    )


    // OK QUAND les OFFSET -, PAS OK DANS LES OFFSET + ???


        if (square.isNotEmpty) {
            Image(
                painter = painterResource(id = square.piece!!.asset),
                contentDescription = "pieces",
                modifier = Modifier
                    .fillMaxSize(square.piece.assetRatio)
                    .offset{offset}
                    /*.clickable(interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        //moved = !moved
                    }*/
            )
        }


}