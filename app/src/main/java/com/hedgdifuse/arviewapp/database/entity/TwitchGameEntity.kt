package com.hedgdifuse.arviewapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hedgdifuse.arviewapp.Constants
import com.hedgdifuse.arviewapp.model.TwitchGame
import com.hedgdifuse.arviewapp.model.TwitchGameStats

@Entity(tableName = Constants.TABLE_NAME)
data class TwitchGameEntity(
    @PrimaryKey val id: Int,
    val channels: Int,
    val viewers: Int,
    val game: TwitchGameStats
) {
    fun toTwitchGame() =
        TwitchGame(channels, viewers, game)
}
