package org.placidfox.jetpackchess.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.placidfox.jetpackchess.viewModel.GameViewModel



@Composable
fun Toolbar(viewModel: GameViewModel){
    Row (modifier = Modifier
        .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        
    ){

        ArrowButton(ArrowButtonType.FIRST_POSITION, { viewModel.changeActivePosition(0) }, !viewModel.uiState.isActivePositionFirst)
        ArrowButton(ArrowButtonType.PREVIOUS_POSITION, { viewModel.backActivePosition() }, !viewModel.uiState.isActivePositionFirst)
        SwitchOrientation(viewModel)
        ArrowButton(ArrowButtonType.NEXT_POSITION, { viewModel.forwardActivePosition() }, !viewModel.uiState.isActivePositionLast)
        ArrowButton(ArrowButtonType.LAST_POSITION, { viewModel.changeActivePosition(viewModel.gameTimeline.positionsTimeline.lastIndex) }, !viewModel.uiState.isActivePositionLast)

    }

}

enum class ArrowButtonType(val icon: ImageVector, val description: String){
    FIRST_POSITION(Icons.Default.ArrowBack, "First Position Button"),
    PREVIOUS_POSITION(Icons.Default.KeyboardArrowLeft, "Previous Position Button"),
    NEXT_POSITION(Icons.Default.KeyboardArrowRight, "Next Position Button"),
    LAST_POSITION(Icons.Default.ArrowForward, "Last Position Button")
}

@Composable
fun ArrowButton(arrowButton: ArrowButtonType, action: () -> Unit, enabler: Boolean){
    Button(
        onClick = action,
        enabled = enabler

    ){
        Icon(arrowButton.icon, contentDescription = arrowButton.description)
    }

}


@Composable
fun SwitchOrientation(viewModel: GameViewModel){

    Button(
        onClick = {
            viewModel.switchBoardOrientation()
        }
    ){
        Icon(Icons.Default.Refresh, contentDescription = "switch_Icon")
    }

}