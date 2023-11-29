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
import org.placidfox.jetpackchess.viewModel.GameViewModel


@Composable
fun Coordinate(viewModel: GameViewModel, square: Square){

    when (viewModel.boardOrientationState.value){

        PlayerColor.WHITE -> {

            if (square.coordinate.file == 8) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.TopEnd){

                    val boxWithConstraintsScope = this

                    Text(text = square.coordinate.rank.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.isLight) {
                            viewModel.boardColorState.value.darkSquareColor
                        } else {
                            viewModel.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }
            if (square.coordinate.rank == 1) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.BottomStart
                ) {

                    val boxWithConstraintsScope = this

                    Text(
                        text = square.coordinate.fileLetter.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.coordinate.isLightSquare()) {
                            viewModel.boardColorState.value.darkSquareColor
                        } else {
                            viewModel.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }


        }

        PlayerColor.BLACK -> {
            if (square.coordinate.file == 1) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.TopEnd){

                    val boxWithConstraintsScope = this

                    Text(text = square.coordinate.rank.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.isLight) {
                            viewModel.boardColorState.value.darkSquareColor
                        } else {
                            viewModel.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }


            if (square.coordinate.rank == 8) {

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(0.95f),
                    contentAlignment = Alignment.BottomStart
                ) {

                    val boxWithConstraintsScope = this

                    Text(
                        text = square.coordinate.fileLetter.toString(),
                        fontSize = boxWithConstraintsScope.maxHeight.value.sp / 5,
                        fontWeight = FontWeight.Bold,
                        color = if (square.isLight) {
                            viewModel.boardColorState.value.darkSquareColor
                        } else {
                            viewModel.boardColorState.value.lightSquareColor
                        }
                    )

                }
            }

        }

    }



}

