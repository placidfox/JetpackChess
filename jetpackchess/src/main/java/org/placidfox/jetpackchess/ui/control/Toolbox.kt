package org.placidfox.jetpackchess.ui.control

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import org.placidfox.jetpackchess.viewModel.UIViewModel


@Composable
fun SwitchOrientation(uiState: UIViewModel){

    Button(onClick = {uiState.switchOrientation()}){
        Icon(Icons.Default.Refresh, contentDescription = "switch_Icon")
    }

}