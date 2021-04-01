package com.hedgdifuse.arviewapp.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hedgdifuse.arviewapp.model.TwitchGameStats

class TwitchGameStatsConverter {
    @TypeConverter
    fun converterFromString(input: String): TwitchGameStats =
        Gson().fromJson(input, object : TypeToken<TwitchGameStats>() {}.type)

    @TypeConverter
    fun convertToString(input: TwitchGameStats): String =
        Gson().toJson(input)
}