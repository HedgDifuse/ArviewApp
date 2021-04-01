package com.hedgdifuse.arviewapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hedgdifuse.arviewapp.database.dao.TwitchGamesDatabase
import com.hedgdifuse.arviewapp.model.TwitchGame
import com.hedgdifuse.arviewapp.router.RouterState
import com.hedgdifuse.arviewapp.router.TwitchRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException

@OptIn(KoinApiExtension::class)
class TwitchGamesViewModel: ViewModel(), KoinComponent {

    // Inject needed classes
    private val router: TwitchRouter by inject()
    private val coroutineScope: CoroutineScope by inject()
    private val database: TwitchGamesDatabase by inject()

    private var size = 0
    private var databaseChecked = false

    val games: MutableLiveData<RouterState<List<TwitchGame>>> by lazy {
        MutableLiveData()
    }

    fun fetchGames(
        limit: Int = 20,
        offset: Int = size,
        clearDatabase: Boolean = false
    ) = coroutineScope.launch {

        /**
         * Get values from database only one time.
         */
        if(!databaseChecked) {
            val items = try {
                database.gamesDao().games()
            } catch(e: Exception) {
                println("got an error: ${e::class}")
                emptyList()
            }

            size = items.size

            games.postValue(
                RouterState.RouterStateSuccess(items.map { it.toTwitchGame() }, false)
            )

            databaseChecked = true
        } else {
            // Set loading state
            games.postValue(RouterState.RouterStateLoading(size == 0))
        }

        val refresh = size == 0

        // Set result state
        with(database.gamesDao()) {
            try {
                val response =
                    RouterState.RouterStateSuccess(router.games(limit, offset).top, clearDatabase)

                if (clearDatabase) size = response.data.size
                else               size += response.data.size

                // Send result to view
                games.postValue(response)

                // Insert values into database
                if(clearDatabase) clear()
                insertAll(*response.data.map { it.toTwitchGameEntity(it.game.id) }.toTypedArray())

            } catch (e: Exception) {
                println("got an error: ${e::class}")
                games.postValue(RouterState.RouterStateErrored(e, refresh))
            }
        }
    }
}