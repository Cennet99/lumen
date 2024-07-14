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
import com.cennetnadir.lumen.databinding.FragmentAddDeckBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddDeckFragment : Fragment() {

    private var _binding: FragmentAddDeckBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDeckBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreateDeck.setOnClickListener {
            val deckName = binding.deckNameInput.text.toString().trim()
            if (deckName.isNotBlank()) {
                createDeck(deckName)
            } else {
                Toast.makeText(requireContext(), "Please enter a deck name.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createDeck(deckName: String) {
        val deckId = UUID.randomUUID().toString()
        val deck = Deck(id = deckId, name = deckName)
        firestore.collection("decks")
            .document(deckId)
            .set(deck)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Deck created", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putParcelable("deck", deck)
                }
                findNavController().navigate(R.id.actionAddDeckFragmentToAddFlashcardFragment, bundle)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error creating deck: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
