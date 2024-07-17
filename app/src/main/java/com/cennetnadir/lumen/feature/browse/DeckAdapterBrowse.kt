package com.cennetnadir.lumen.feature.browse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.ItemDeckBrowseBinding

class DeckAdapterBrowse(
    private val decks: MutableList<Deck>,
    private val onLearnClick: (Deck) -> Unit
) : RecyclerView.Adapter<DeckAdapterBrowse.DeckViewHolder>() {

    inner class DeckViewHolder(val binding: ItemDeckBrowseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val binding = ItemDeckBrowseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeckViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val deck = decks[position]
        holder.binding.deck = deck
        holder.binding.buttonLearnDeck.setOnClickListener { onLearnClick(deck) }
    }

    override fun getItemCount(): Int = decks.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateDecks(newDecks: List<Deck>) {
        decks.clear()
        decks.addAll(newDecks)
        notifyDataSetChanged()
    }
}
