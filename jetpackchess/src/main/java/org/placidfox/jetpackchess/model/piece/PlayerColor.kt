package org.placidfox.jetpackchess.model.piece

enum class PlayerColor {
    WHITE,
    BLACK;

    fun opponent(): PlayerColor {
        return when(this){
            WHITE -> BLACK
            BLACK -> WHITE
        }
    }
}
