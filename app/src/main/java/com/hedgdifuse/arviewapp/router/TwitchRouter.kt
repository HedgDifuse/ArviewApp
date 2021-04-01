package com.hedgdifuse.arviewapp.router

import com.hedgdifuse.arviewapp.model.TwitchGamesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [TwitchRouter] - interface for retrofit.
 */
interface TwitchRouter {

    @GET("kraken/games/top")
    suspend fun games(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): TwitchGamesResponse
}