package org.placidfox.jetpackchess.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import org.placidfox.jetpackchess.viewModel.UIViewModel



@Composable
fun Toolbar(uiState: UIViewModel){
    Row (modifier = Modifier
        .fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        
    ){
        ArrowButton(ArrowButtonType.FIRST_POSITION, { uiState.changeActivePosition(0) }, uiState.isFirstPosition)
        ArrowButton(ArrowButtonType.PREVIOUS_POSITION, { uiState.backActivePosition() }, uiState.isActivePositionFirst)
        SwitchOrientation(uiState)
        ArrowButton(ArrowButtonType.NEXT_POSITION, { uiState.forwardActivePosition() }, uiState.isActivePositionLast)
        ArrowButton(ArrowButtonType.LAST_POSITION, { uiState.changeActivePosition(uiState.gameTimeline.positionsTimeline.lastIndex) }, uiState.isLastPosition)

    }

}

enum class ArrowButtonType(val icon: ImageVector, val description: String){
    FIRST_POSITION(Icons.Default.ArrowBack, "First Position Button"),
    PREVIOUS_POSITION(Icons.Default.KeyboardArrowLeft, "Previous Position Button"),
    NEXT_POSITION(Icons.Default.KeyboardArrowRight, "Next Position Button"),
    LAST_POSITION(Icons.Default.ArrowForward, "Last Position Button")
}

@Composable
fun ArrowButton(arrowButton: ArrowButtonType, action: () -> Unit, enabler: MutableState<Boolean>){
    Button(
        onClick = action,
        enabled = enabler.value

    ){
        Icon(arrowButton.icon, contentDescription = arrowButton.description)
    }

}


@Composable
fun SwitchOrientation(uiState: UIViewModel){

    Button(
        onClick = {uiState.switchOrientation()}
    ){
        Icon(Icons.Default.Refresh, contentDescription = "switch_Icon")
    }

}