package org.placidfox.jetpackchess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.placidfox.jetpackchess.controller.Controller
import org.placidfox.jetpackchess.ui.board.BoardComposable
import org.placidfox.jetpackchess.ui.control.PromotionDialog
import org.placidfox.jetpackchess.ui.control.Toolbar


@Composable
fun JetpackChess(controller: Controller){

    Column (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        BoardComposable(controller.viewModel)
        Toolbar(controller.viewModel)

        if(controller.viewModel.showPromotionDialog.value) {
            PromotionDialog(controller.viewModel)
        }

    }

}

