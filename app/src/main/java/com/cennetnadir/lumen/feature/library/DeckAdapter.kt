package com.cennetnadir.lumen.feature.library

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.databinding.ItemDeckBinding

class DeckAdapter(
    private val decks: MutableList<Deck>,
    private val onEditClick: (Deck) -> Unit,
    private val onLearnClick: (Deck) -> Unit,
    private val onDeleteClick: (Deck) -> Unit
) : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    inner class DeckViewHolder(val binding: ItemDeckBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val binding = ItemDeckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeckViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val deck = decks[position]
        holder.binding.deck = deck
        holder.binding.buttonEditDeck.setOnClickListener { onEditClick(deck) }
        holder.binding.buttonLearnDeck.setOnClickListener { onLearnClick(deck) }
        holder.binding.buttonDeleteDeck.setOnClickListener { onDeleteClick(deck) }
    }

    override fun getItemCount(): Int = decks.size

    fun removeDeck(deck: Deck) {
        val position = decks.indexOf(deck)
        if (position != -1) {
            decks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDecks(newDecks: List<Deck>) {
        decks.clear()
        decks.addAll(newDecks)
        notifyDataSetChanged()
    }
}
