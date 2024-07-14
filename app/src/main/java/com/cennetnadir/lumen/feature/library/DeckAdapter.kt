package com.cennetnadir.lumen.feature.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck

class DeckAdapter(private val deckList: List<Deck>) : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false)
        return DeckViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        holder.bind(deckList[position])
    }

    override fun getItemCount(): Int = deckList.size

    class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deckNameTextView: TextView = itemView.findViewById(R.id.deck_name)

        fun bind(deck: Deck) {
            deckNameTextView.text = deck.name
        }
    }
}
