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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddDeckFragment : Fragment() {

    // View binding instance
    private var _binding: FragmentAddDeckBinding? = null
    private val binding get() = _binding!!

    // Firebase Firestore and Authentication instances
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentAddDeckBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for the create deck button
        binding.buttonCreateDeck.setOnClickListener {
            // Retrieve the deck name from the input field
            val deckName = binding.deckNameInput.text.toString().trim()
            if (deckName.isNotBlank()) {
                // If the deck name is not blank, create a new deck
                createDeck(deckName)
            } else {
                // Show a message if the deck name is blank
                Toast.makeText(requireContext(), "Please enter a deck name.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to create a new deck
    private fun createDeck(deckName: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Generate a unique ID for the deck
            val deckId = UUID.randomUUID().toString()
            // Create a new Deck object
            val deck = Deck(id = deckId, userId = currentUser.uid, name = deckName)
            // Add the deck to the Firestore collection
            firestore.collection("decks")
                .document(deckId)
                .set(deck)
                .addOnSuccessListener {
                    // Show a message when the deck is successfully created
                    Toast.makeText(requireContext(), "Deck created", Toast.LENGTH_SHORT).show()
                    // Prepare data for the next fragment
                    val bundle = Bundle().apply {
                        putParcelable("deck", deck)
                    }
                    // Navigate to the AddFlashcardFragment
                    findNavController().navigate(R.id.action_addDeckFragment_to_addFlashcardFragment, bundle)
                }
                .addOnFailureListener { e ->
                    // Show a message if there is an error
                    Toast.makeText(requireContext(), "Error creating deck: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Show a message if the user is not logged in
            Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}
