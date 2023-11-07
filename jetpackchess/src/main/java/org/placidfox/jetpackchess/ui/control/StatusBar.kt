package org.placidfox.jetpackchess.ui.control

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.placidfox.jetpackchess.viewModel.UIViewModel

@Composable
fun StatusBar (uiState: UIViewModel) {

    Text(color = Color.Black, text = uiState.textState.value)

}
