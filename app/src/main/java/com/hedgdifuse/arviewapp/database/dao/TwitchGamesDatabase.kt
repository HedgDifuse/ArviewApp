package com.hedgdifuse.arviewapp.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hedgdifuse.arviewapp.database.converter.TwitchGameStatsConverter
import com.hedgdifuse.arviewapp.database.entity.TwitchGameEntity

@Database(entities = [TwitchGameEntity::class], version = 1)
@TypeConverters(TwitchGameStatsConverter::class)
abstract class TwitchGamesDatabase: RoomDatabase() {
    abstract fun gamesDao(): TwitchGamesDAO
}