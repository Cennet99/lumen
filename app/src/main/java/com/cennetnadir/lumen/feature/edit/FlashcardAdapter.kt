package com.cennetnadir.lumen.feature.edit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.ItemFlashcardBinding

class FlashcardAdapter(
    private var flashcards: List<Flashcard>,
    private val onEditClick: (Flashcard) -> Unit,
    private val onDeleteClick: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashcardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.bind(flashcard)
    }

    override fun getItemCount(): Int = flashcards.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateFlashcards(newFlashcards: List<Flashcard>) {
        flashcards = newFlashcards
        notifyDataSetChanged()
    }

    inner class FlashcardViewHolder(private val binding: ItemFlashcardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(flashcard: Flashcard) {
            binding.textViewQuestion.text = flashcard.question
            binding.textViewAnswer.text = flashcard.answer

            binding.buttonEditFlashcard.setOnClickListener {
                onEditClick(flashcard)
            }

            binding.buttonDeleteFlashcard.setOnClickListener {
                onDeleteClick(flashcard)
            }
        }
    }
}
