package com.hedgdifuse.arviewapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hedgdifuse.arviewapp.R
import com.hedgdifuse.arviewapp.databinding.ItemGameCardBinding
import com.hedgdifuse.arviewapp.model.TwitchGame
import java.text.NumberFormat

class GameItemAdapter: RecyclerView.Adapter<GameItemAdapter.GameItemViewHolder>() {

    var data = ArrayList<TwitchGame>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_game_card, parent, false)
        )

    override fun onBindViewHolder(holder: GameItemViewHolder, position: Int) {
        with(holder.binding) {
            val item = data[position]
            val context = root.context
            val numberFormat = NumberFormat.getNumberInstance()

            gameImage.setImageURI(item.game.box["large"])
            gameName.text = item.game.name

            gameChannelsCount.text = context
                .getString(R.string.channels_count)
                .format(numberFormat.format(item.channels))

            gameViewersCount.text = context
                .getString(R.string.viewers_count)
                .format(numberFormat.format(item.viewers))
        }
    }

    inner class GameItemViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val binding = ItemGameCardBinding.bind(itemView)
    }

    /**
     * list modification methods
     */
    fun append(items: Collection<TwitchGame>) {
        val oldSize = data.size

        data.addAll(items)

        notifyItemRangeChanged(oldSize, data.size)
    }
}