package com.hedgdifuse.arviewapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hedgdifuse.arviewapp.R
import com.hedgdifuse.arviewapp.databinding.ActivityMainBinding
import com.hedgdifuse.arviewapp.model.TwitchGame
import com.hedgdifuse.arviewapp.presentation.adapter.GameItemAdapter
import com.hedgdifuse.arviewapp.presentation.viewmodel.TwitchGamesViewModel
import com.hedgdifuse.arviewapp.router.RouterState
import org.koin.android.ext.android.bind

class MainActivity : AppCompatActivity() {

    private val gamesViewModel: TwitchGamesViewModel by viewModels()
    private val adapter = GameItemAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set other theme for splash screen feature
        setTheme(R.style.Theme_ArviewApp)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ui, add viewmodel observer
        with(binding.mainRecycler) {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(context)

            setOnScrollChangeListener { _, _, _, _, _ ->
                if(!canScrollVertically(1)) {
                    gamesViewModel.fetchGames()
                }
            }
        }

        with(binding.mainSwipeRefresh) {
            setColorSchemeResources(R.color.colorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.colorSurface)

            // Refresh
            setOnRefreshListener {
                gamesViewModel.fetchGames(clearDatabase = true)
            }
        }

        // Retry on error
        binding.mainRetryButton.setOnClickListener {
            gamesViewModel.fetchGames(clearDatabase = true)
        }

        gamesViewModel.games.observe(this) {

            // Set screen state
            binding.mainRecycler.isVisible = it is RouterState.RouterStateSuccess || !it.refresh
            binding.mainProgress.isVisible = it is RouterState.RouterStateLoading
            binding.mainError.isVisible =    it is RouterState.RouterStateErrored && it.refresh

            // Set data, or show snackbar if data size > 0
            when(it) {
                is RouterState.RouterStateSuccess -> {
                    if(it.refresh) {
                        adapter.data = ArrayList(it.data)
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.append(it.data)
                    }

                    binding.mainSwipeRefresh.isRefreshing = false
                }

                is RouterState.RouterStateErrored -> {
                    if(!it.refresh) {
                        Snackbar
                            .make(
                                binding.root,
                                R.string.error_when_loading,
                                BaseTransientBottomBar.LENGTH_INDEFINITE
                            )
                            .setAction(R.string.retry) { gamesViewModel.fetchGames() }
                            .show()
                    }
                }
            }
        }

        // Fetch data on finish initialization
        gamesViewModel.fetchGames()
    }
}