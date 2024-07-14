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

    private var _binding: FragmentAddFlashcardBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var deck: Deck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFlashcardBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deck = arguments?.getParcelable("deck")!!

        binding.buttonAddFlashcard.setOnClickListener {
            val question = binding.questionInput.text.toString().trim()
            val answer = binding.answerInput.text.toString().trim()
            if (question.isNotBlank() && answer.isNotBlank()) {
                addFlashcard(question, answer)
            } else {
                Toast.makeText(requireContext(), "Please enter both question and answer.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDone.setOnClickListener {
            findNavController().navigate(R.id.navigation_library)
        }
    }

    private fun addFlashcard(question: String, answer: String) {
        val flashcardId = UUID.randomUUID().toString()
        val flashcard = Flashcard(id = flashcardId, question = question, answer = answer)
        firestore.collection("decks").document(deck.id)
            .update("flashcards", FieldValue.arrayUnion(flashcard))
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Flashcard added", Toast.LENGTH_SHORT).show()
                binding.questionInput.text.clear()
                binding.answerInput.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error adding flashcard: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
