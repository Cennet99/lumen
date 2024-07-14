package com.cennetnadir.lumen.feature.edit

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
    private lateinit var adapter: FlashcardAdapter
    private lateinit var deck: Deck

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditDeckBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deck = arguments?.getParcelable("deck") ?: return

        binding.recyclerViewFlashcards.layoutManager = LinearLayoutManager(requireContext())

        loadFlashcards()

        binding.buttonAddFlashcard.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("deck", deck)
            }
            findNavController().navigate(R.id.action_editDeckFragment_to_addFlashcardFragment, bundle)
        }
    }

    private fun loadFlashcards() {
        firestore.collection("decks").document(deck.id).collection("flashcards")
            .get()
            .addOnSuccessListener { result ->
                val flashcards = result.toObjects(Flashcard::class.java)
                adapter = FlashcardAdapter(flashcards, this::onEditClick, this::onDeleteClick)
                binding.recyclerViewFlashcards.adapter = adapter
            }
    }

    private fun onEditClick(flashcard: Flashcard) {
        // Implementieren Sie die Funktion zum Bearbeiten von Flashcards
    }

    private fun onDeleteClick(flashcard: Flashcard) {
        firestore.collection("decks").document(deck.id).collection("flashcards").document(flashcard.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Flashcard deleted", Toast.LENGTH_SHORT).show()
                loadFlashcards() // Reload flashcards after deleting one
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
