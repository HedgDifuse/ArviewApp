package com.hedgdifuse.arviewapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hedgdifuse.arviewapp.Constants.TABLE_NAME
import com.hedgdifuse.arviewapp.database.entity.TwitchGameEntity

@Dao
interface TwitchGamesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg item: TwitchGameEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun games(): List<TwitchGameEntity>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clear()
}