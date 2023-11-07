package org.placidfox.jetpackchess.ui.board

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.placidfox.jetpackchess.model.board.Coordinate.Companion.isLightSquare
import org.placidfox.jetpackchess.model.board.Square
import org.placidfox.jetpackchess.model.piece.PlayerColor
import org.placidfox.jetpackchess.viewModel.UIViewModel


@Composable
fun Coordinate(uiState: UIViewModel, square: Square){

    when (uiState.boardOrientationState.value){

        PlayerColor.WHITE -> {

            if (square.position.file == 8) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.TopEnd){

                    val boxWithConstraintsScope = this

                    Text(text = square.position.rank.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.isLight) {
                            uiState.boardColorState.value.darkSquareColor
                        } else {
                            uiState.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }
            if (square.position.rank == 1) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.BottomStart
                ) {

                    val boxWithConstraintsScope = this

                    Text(
                        text = square.position.fileLetter.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.position.isLightSquare()) {
                            uiState.boardColorState.value.darkSquareColor
                        } else {
                            uiState.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }


        }

        PlayerColor.BLACK -> {
            if (square.position.file == 1) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.TopEnd){

                    val boxWithConstraintsScope = this

                    Text(text = square.position.rank.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.isLight) {
                            uiState.boardColorState.value.darkSquareColor
                        } else {
                            uiState.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }


            if (square.position.rank == 8) {

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.BottomStart
                ) {

                    val boxWithConstraintsScope = this

                    Text(
                        text = square.position.fileLetter.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.isLight) {
                            uiState.boardColorState.value.darkSquareColor
                        } else {
                            uiState.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }

        }

    }



}

