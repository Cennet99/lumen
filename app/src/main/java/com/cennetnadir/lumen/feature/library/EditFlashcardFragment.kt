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

    private var _binding: FragmentEditFlashcardBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var flashcard: Flashcard
    private var deckId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFlashcardBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flashcard = arguments?.getParcelable("flashcard") ?: return
        deckId = arguments?.getString("deckId")

        binding.editTextQuestion.setText(flashcard.question)
        binding.editTextAnswer.setText(flashcard.answer)

        binding.buttonSave.setOnClickListener {
            saveFlashcard()
        }
    }

    private fun saveFlashcard() {
        val updatedFlashcard = flashcard.copy(
            question = binding.editTextQuestion.text.toString(),
            answer = binding.editTextAnswer.text.toString()
        )

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
