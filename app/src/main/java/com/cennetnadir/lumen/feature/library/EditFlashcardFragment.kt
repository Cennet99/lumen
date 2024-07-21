package com.cennetnadir.lumen.feature.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.FragmentEditFlashcardBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class EditFlashcardFragment : Fragment() {

    // View binding instance to access layout views
    private var _binding: FragmentEditFlashcardBinding? = null
    private val binding get() = _binding!!
    // Firebase Firestore instance for database operations
    private lateinit var firestore: FirebaseFirestore
    // Flashcard instance to hold the flashcard data
    private lateinit var flashcard: Flashcard
    // ID of the deck containing the flashcard
    private var deckId: String? = null

    // Inflates the layout for this fragment using view binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFlashcardBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    // Called after the view has been created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the flashcard and deck ID passed as arguments
        flashcard = arguments?.getParcelable("flashcard") ?: return
        deckId = arguments?.getString("deckId")

        // Set the flashcard data in the input fields
        binding.editTextQuestion.setText(flashcard.question)
        binding.editTextAnswer.setText(flashcard.answer)

        // Set click listener to save the updated flashcard
        binding.buttonSave.setOnClickListener {
            saveFlashcard()
        }
    }

    // Save the updated flashcard to Firestore
    private fun saveFlashcard() {
        val updatedFlashcard = flashcard.copy(
            question = binding.editTextQuestion.text.toString(),
            answer = binding.editTextAnswer.text.toString()
        )

        // Update the flashcard in Firestore
        deckId?.let { id ->
            firestore.collection("decks").document(id)
                .update("flashcards", FieldValue.arrayRemove(flashcard))
                .addOnSuccessListener {
                    firestore.collection("decks").document(id)
                        .update("flashcards", FieldValue.arrayUnion(updatedFlashcard))
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Flashcard updated", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Error updating flashcard: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error updating flashcard: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Clear the binding when the view is destroyed to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
