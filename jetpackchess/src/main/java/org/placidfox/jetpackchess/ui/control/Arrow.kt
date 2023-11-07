package org.placidfox.jetpackchess.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.placidfox.jetpackchess.viewModel.UIViewModel

@Composable
fun Arrow (uiState: UIViewModel) {

    Row (modifier = Modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {uiState.changeActivePosition(0)},
            enabled = !uiState.isFirstPosition.value

        ){
            Text("First")
        }
        Button(onClick = {uiState.backActivePosition()},
            enabled = uiState.isActivePositionFirst.value

        ){
            Text("Back")
        }
        Button(onClick = {uiState.forwardActivePosition()
        },
            enabled = uiState.isActivePositionLast.value
        ) {
            Text("Forward")
        }
        Button(onClick = {uiState.changeActivePosition(uiState.gameTimeline.positionsTimeline.lastIndex)
        },
            enabled = !uiState.isLastPosition.value
        ) {
            Text("Last")
        }

    }


}