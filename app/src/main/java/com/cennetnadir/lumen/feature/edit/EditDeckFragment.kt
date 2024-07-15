package com.cennetnadir.lumen.feature.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cennetnadir.lumen.R
import com.cennetnadir.lumen.core.data.Deck
import com.cennetnadir.lumen.core.data.Flashcard
import com.cennetnadir.lumen.databinding.FragmentEditDeckBinding
import com.google.firebase.firestore.FirebaseFirestore

class EditDeckFragment : Fragment() {

    private var _binding: FragmentEditDeckBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var deck: Deck
    private lateinit var adapter: FlashcardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDeckBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deck = arguments?.getParcelable("deck") ?: return

        binding.deckTitle.text = "Viewing Deck: ${deck.name}"
        binding.recyclerViewFlashcards.layoutManager = LinearLayoutManager(requireContext())

        firestore.collection("decks").document(deck.id).get().addOnSuccessListener { document ->
            val retrievedDeck = document.toObject(Deck::class.java)
            if (retrievedDeck != null) {
                deck = retrievedDeck
                adapter = FlashcardAdapter(deck.flashcards, this::onEditClick, this::onDeleteClick)
                binding.recyclerViewFlashcards.adapter = adapter
            }
        }

        binding.buttonAddFlashcard.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("deck", deck)
            }
            findNavController().navigate(R.id.action_editDeckFragment_to_addFlashcardFragment, bundle)
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onEditClick(flashcard: Flashcard) {
        val bundle = Bundle().apply {
            putParcelable("flashcard", flashcard)
            putString("deckId", deck.id)
        }
        findNavController().navigate(R.id.action_editDeckFragment_to_editFlashcardFragment, bundle)
    }

    private fun onDeleteClick(flashcard: Flashcard) {
        firestore.collection("decks").document(deck.id)
            .update("flashcards", com.google.firebase.firestore.FieldValue.arrayRemove(flashcard))
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Flashcard deleted", Toast.LENGTH_SHORT).show()
                // Remove the flashcard from the local list and notify the adapter
                val updatedFlashcards = deck.flashcards.toMutableList().apply {
                    remove(flashcard)
                }
                deck = deck.copy(flashcards = updatedFlashcards)
                adapter.updateFlashcards(updatedFlashcards)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error deleting flashcard: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
