package com.hedgdifuse.arviewapp.router

import com.hedgdifuse.arviewapp.Constants.START_URL
import com.hedgdifuse.arviewapp.Constants.TOKEN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [TwitchRouterFactory] - factory for create router instance
 */
object TwitchRouterFactory {
    private val client = OkHttpClient
        .Builder()
        .addInterceptor {
            it.proceed(
                it.request()
                    .newBuilder()
                    .addHeader("Client-ID", TOKEN)
                    .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                    .build()
            )
        }

    fun create(): TwitchRouter {
        val retrofit = Retrofit.Builder()
            .baseUrl(START_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        return retrofit.create(TwitchRouter::class.java)
    }
}