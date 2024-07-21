package com.cennetnadir.lumen.feature.edit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.ItemFlashcardBinding

class FlashcardAdapter(
    private var flashcards: List<Flashcard>,
    private val onEditClick: (Flashcard) -> Unit,
    private val onDeleteClick: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    // Creates a new ViewHolder for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashcardViewHolder(binding)
    }

    // Binds the data to the views in each ViewHolder
    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.bind(flashcard)
    }

    // Returns the total number of items in the RecyclerView
    override fun getItemCount(): Int = flashcards.size

    // Updates the list of flashcards and notifies the adapter to refresh the views
    @SuppressLint("NotifyDataSetChanged")
    fun updateFlashcards(newFlashcards: List<Flashcard>) {
        flashcards = newFlashcards
        notifyDataSetChanged()
    }

    // ViewHolder class to hold the views for each item in the RecyclerView
    inner class FlashcardViewHolder(private val binding: ItemFlashcardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(flashcard: Flashcard) {
            binding.textViewQuestion.text = flashcard.question
            binding.textViewAnswer.text = flashcard.answer

            // Set click listener for edit button
            binding.buttonEditFlashcard.setOnClickListener {
                onEditClick(flashcard)
            }

            // Set click listener for delete button
            binding.buttonDeleteFlashcard.setOnClickListener {
                onDeleteClick(flashcard)
            }
        }
    }
}
