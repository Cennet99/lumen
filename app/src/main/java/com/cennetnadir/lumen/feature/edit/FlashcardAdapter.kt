package com.cennetnadir.lumen.feature.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.ItemFlashcardBinding

class FlashcardAdapter(
    private var flashcards: List<Flashcard>,
    private val editClickListener: (Flashcard) -> Unit,
    private val deleteClickListener: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    inner class FlashcardViewHolder(private val binding: ItemFlashcardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flashcard: Flashcard) {
            binding.textViewQuestion.text = flashcard.question
            binding.textViewAnswer.text = flashcard.answer

            binding.buttonEdit.setOnClickListener { editClickListener(flashcard) }
            binding.buttonDelete.setOnClickListener { deleteClickListener(flashcard) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashcardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        holder.bind(flashcards[position])
    }

    override fun getItemCount(): Int = flashcards.size

    fun updateFlashcards(newFlashcards: List<Flashcard>) {
        flashcards = newFlashcards
        notifyDataSetChanged()
    }
}
