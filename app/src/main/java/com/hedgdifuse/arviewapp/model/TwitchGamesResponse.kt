package com.hedgdifuse.arviewapp.model

import com.google.gson.annotations.SerializedName
import com.hedgdifuse.arviewapp.database.entity.TwitchGameEntity

data class TwitchGamesResponse(
    @SerializedName("_total") val total: Int,
    val top: List<TwitchGame>
)

data class TwitchGame(
    val channels: Int,
    val viewers: Int,
    val game: TwitchGameStats
) {
    fun toTwitchGameEntity(id: Int) =
        TwitchGameEntity(id, channels, viewers, game)
}

/**
 * @param box object of game image
 * @param logo object of game logo
 *
 * Box and logo keys: large, medium, small and template
 */
data class TwitchGameStats(
    @SerializedName("_id") val id: Int,
    val box: Map<String, String>,
    @SerializedName("giantbomb_id") val giantbombId: Long,
    val logo: Map<String, String>,
    val name: String
)