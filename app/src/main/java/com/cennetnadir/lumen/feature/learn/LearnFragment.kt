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

    // View binding instance to access layout views
    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    // Deck instance to hold the deck data
    private lateinit var deck: Deck
    // List of all flashcards in the deck
    private lateinit var flashcards: MutableList<Flashcard>
    // List of flashcards remaining to be reviewed
    private lateinit var remainingFlashcards: MutableList<Flashcard>
    // Index of the current flashcard being reviewed
    private var currentFlashcardIndex: Int = 0

    // Inflate the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Called after the view has been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the deck passed as an argument
        deck = arguments?.getParcelable("deck")!!
        flashcards = deck.flashcards.toMutableList()
        remainingFlashcards = flashcards.toMutableList()

        // Set the deck name in the UI
        binding.textViewDeckName.text = deck.name

        // Show the first flashcard
        showNextFlashcard()

        // Set click listener to show the answer and options
        binding.buttonShowAnswer.setOnClickListener {
            binding.textViewAnswer.visibility = View.VISIBLE
            binding.buttonKnown.visibility = View.VISIBLE
            binding.buttonUnknown.visibility = View.VISIBLE
            binding.buttonShowAnswer.visibility = View.GONE
        }

        // Set click listener to mark the flashcard as known and show the next one
        binding.buttonKnown.setOnClickListener {
            markFlashcardAsKnown()
            showNextFlashcard()
        }

        // Set click listener to show the next flashcard without marking it as known
        binding.buttonUnknown.setOnClickListener {
            showNextFlashcard()
        }
    }

    // Show the next flashcard
    private fun showNextFlashcard() {
        binding.textViewAnswer.visibility = View.GONE
        binding.buttonKnown.visibility = View.GONE
        binding.buttonUnknown.visibility = View.GONE
        binding.buttonShowAnswer.visibility = View.VISIBLE

        // If all flashcards have been reviewed, navigate back to home
        if (remainingFlashcards.isEmpty()) {
            if (flashcards.isEmpty()) {
                findNavController().navigate(R.id.action_learnFragment_to_navigation_congrats)
            } else {
                remainingFlashcards = flashcards.toMutableList()
            }
        }

        // Show the next flashcard if there are remaining flashcards
        if (remainingFlashcards.isNotEmpty()) {
            val flashcard = remainingFlashcards.removeAt(0)
            binding.textViewQuestion.text = flashcard.question
            binding.textViewAnswer.text = flashcard.answer
        }
    }

    // Mark the current flashcard as known
    private fun markFlashcardAsKnown() {
        val flashcard = flashcards.removeAt(0)
        if (remainingFlashcards.contains(flashcard)) {
            remainingFlashcards.remove(flashcard)
        }
    }

    // Clear the binding when the view is destroyed to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
