package com.cennetnadir.lumen.feature.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.FragmentAddFlashcardBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import java.util.UUID

class AddFlashcardFragment : Fragment() {

    // View binding instance
    private var _binding: FragmentAddFlashcardBinding? = null
    private val binding get() = _binding!!

    // Firebase Firestore instance
    private lateinit var firestore: FirebaseFirestore
    // Deck to which flashcards will be added
    private lateinit var deck: Deck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentAddFlashcardBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the deck from arguments
        deck = arguments?.getParcelable("deck")!!

        // Set click listener for the add flashcard button
        binding.buttonAddFlashcard.setOnClickListener {
            // Retrieve the question and answer from the input fields
            val question = binding.questionInput.text.toString().trim()
            val answer = binding.answerInput.text.toString().trim()
            if (question.isNotBlank() && answer.isNotBlank()) {
                // If the question and answer are not blank, add the flashcard to the deck
                addFlashcard(question, answer)
            } else {
                // Show a message if either the question or answer is blank
                Toast.makeText(requireContext(), "Please enter both question and answer.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for the done button
        binding.buttonDone.setOnClickListener {
            // Navigate to the library screen
            findNavController().navigate(R.id.navigation_library)
        }
    }

    // Function to add a flashcard to the deck
    private fun addFlashcard(question: String, answer: String) {
        // Generate a unique ID for the flashcard
        val flashcardId = UUID.randomUUID().toString()
        // Create a new Flashcard object
        val flashcard = Flashcard(id = flashcardId, question = question, answer = answer)
        // Add the flashcard to the Firestore document
        firestore.collection("decks").document(deck.id)
            .update("flashcards", FieldValue.arrayUnion(flashcard))
            .addOnSuccessListener {
                // Show a message when the flashcard is successfully added
                Toast.makeText(requireContext(), "Flashcard added", Toast.LENGTH_SHORT).show()
                // Clear the input fields
                binding.questionInput.text.clear()
                binding.answerInput.text.clear()
            }
            .addOnFailureListener { e ->
                // Show a message if there is an error
                Toast.makeText(requireContext(), "Error adding flashcard: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}
