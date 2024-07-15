package com.cennetnadir.lumen.feature.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private lateinit var deck: Deck
    private lateinit var flashcards: MutableList<Flashcard>
    private lateinit var remainingFlashcards: MutableList<Flashcard>
    private var currentFlashcardIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deck = arguments?.getParcelable("deck")!!
        flashcards = deck.flashcards.toMutableList()
        remainingFlashcards = flashcards.toMutableList()

        binding.textViewDeckName.text = deck.name

        showNextFlashcard()

        binding.buttonShowAnswer.setOnClickListener {
            binding.textViewAnswer.visibility = View.VISIBLE
            binding.buttonKnown.visibility = View.VISIBLE
            binding.buttonUnknown.visibility = View.VISIBLE
            binding.buttonShowAnswer.visibility = View.GONE
        }

        binding.buttonKnown.setOnClickListener {
            markFlashcardAsKnown()
            showNextFlashcard()
        }

        binding.buttonUnknown.setOnClickListener {
            showNextFlashcard()
        }
    }

    private fun showNextFlashcard() {
        binding.textViewAnswer.visibility = View.GONE
        binding.buttonKnown.visibility = View.GONE
        binding.buttonUnknown.visibility = View.GONE
        binding.buttonShowAnswer.visibility = View.VISIBLE

        if (remainingFlashcards.isEmpty()) {
            if (flashcards.isEmpty()) {
                findNavController().navigate(R.id.action_learnFragment_to_navigation_home)
            } else {
                remainingFlashcards = flashcards.toMutableList()
            }
        }

        if (remainingFlashcards.isNotEmpty()) {
            val flashcard = remainingFlashcards.removeAt(0)
            binding.textViewQuestion.text = flashcard.question
            binding.textViewAnswer.text = flashcard.answer
        }
    }

    private fun markFlashcardAsKnown() {
        val flashcard = flashcards.removeAt(0)
        if (remainingFlashcards.contains(flashcard)) {
            remainingFlashcards.remove(flashcard)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
