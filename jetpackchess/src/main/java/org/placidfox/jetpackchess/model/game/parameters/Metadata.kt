package org.placidfox.jetpackchess.model.game.parameters

import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.BLACK_PLAYER
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.DATE
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.EVENT
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_NAME
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_SPECIFIC
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.OPENING_TAGS
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.PUZZLE_RATING
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.RESULT
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.SITE
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.THEMES
import org.placidfox.jetpackchess.model.game.parameters.Metadata.Companion.WHITE_PLAYER

interface Metadata {

    val tags: Map<String, String>

    fun extract(key: String): String? = tags[key]
    companion object {

        //Game specific
        const val EVENT = "Event"
        const val SITE = "Site"
        const val DATE = "Date"
        const val WHITE_PLAYER = "White"
        const val BLACK_PLAYER = "Black"
        const val RESULT = "Result"
        // Puzzle specific
        const val PUZZLE_RATING = "Rating"
        const val THEMES = "Themes"
        const val OPENING_TAGS = "OpeningTags"
        // Opening specific
        const val OPENING_NAME = "OpeningMain"
        const val OPENING_SPECIFIC = "OpeningSpecific"
    }
}

class GameMetadata(override val tags: Map<String, String>): Metadata {

    val event: String? = extract(EVENT)
    val site: String? = extract(SITE)
    val date: String? = extract(DATE)
    val whitePlayer: String? = extract(WHITE_PLAYER)
    val blackPlayer: String? = extract(BLACK_PLAYER)
    val result: String? = extract(RESULT)


    companion object {

         fun initializeMetadata(): GameMetadata =
            GameMetadata(
                tags = mapOf(
                    EVENT to "?",
                    SITE to "?",
                    DATE to "?",
                    WHITE_PLAYER to "?",
                    BLACK_PLAYER to "?",
                    RESULT to "?",
                )
            )

    }
}

class PuzzleMetadata(override val tags: Map<String, String>): Metadata {

    val puzzleRating: String? = extract(PUZZLE_RATING)
    val themes: String? = extract(THEMES)
    val openingTags: String? = extract(OPENING_TAGS)

    companion object {
        fun initializeMetadata(): PuzzleMetadata =
            PuzzleMetadata(
                tags = mapOf(
                    PUZZLE_RATING to "?",
                    THEMES to "?",
                    OPENING_TAGS to "?",
                )
            )

    }

}
class OpeningMetadata(override val tags: Map<String, String>): Metadata {

    val openingName: String? = extract(OPENING_NAME)
    val openingSpecific: String? = extract(OPENING_SPECIFIC)

    companion object {
        fun initializeMetadata(): OpeningMetadata =
            OpeningMetadata(
                tags = mapOf(
                    OPENING_NAME to "?",
                    OPENING_SPECIFIC to "?",
                )
            )

    }
}