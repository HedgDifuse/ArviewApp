package com.hedgdifuse.arviewapp.di

import androidx.room.Room
import com.hedgdifuse.arviewapp.database.dao.TwitchGamesDatabase
import com.hedgdifuse.arviewapp.router.TwitchRouterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModules = listOf(

    // Tools module
    module {
        single { TwitchRouterFactory.create() }
        single { CoroutineScope(Dispatchers.Main.immediate) } bind CoroutineScope::class
        single {
            Room.databaseBuilder(
                get(),
                TwitchGamesDatabase::class.java, "arview-database"
            ).build()
        }
    }
)